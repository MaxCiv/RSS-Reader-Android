package com.maxciv.rssreader.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.maxciv.rssreader.Cache
import com.maxciv.rssreader.model.FeedType
import com.maxciv.rssreader.model.HabrFeed
import com.maxciv.rssreader.model.HabrPost
import com.maxciv.rssreader.network.ApiFactory
import com.maxciv.rssreader.repository.HabrRssRepository
import com.maxciv.rssreader.util.Result
import kotlinx.coroutines.*

/**
 * @author maxim.oleynik
 * @since 06.03.2020
 */
class PostsListViewModel : ViewModel() {

    private val viewModelJob: CompletableJob = SupervisorJob()
    private val viewModelScope: CoroutineScope = CoroutineScope(viewModelJob + Dispatchers.Main)
    private val repository: HabrRssRepository = HabrRssRepository(ApiFactory.getHabrRssApi())

    private val _habrFeed = MutableLiveData<HabrFeed>()
    val habrFeed: LiveData<HabrFeed> = _habrFeed

    fun loadHabrFeed(feedType: FeedType) {
        viewModelScope.launch(Dispatchers.IO) {
            when (val habrPosts = feedType.getHabrPosts(repository)) {
                is Result.Success -> {
                    Cache.addFeedToCache(feedType, habrPosts.data)
                    _habrFeed.postValue(habrPosts.data)
                }
                is Result.Error -> {
                    Cache.addFeedToCache(feedType, HabrFeed())
                    _habrFeed.postValue(HabrFeed())
                }
            }
        }
    }

    fun getHabrFeedFromCache(feedType: FeedType) {
        _habrFeed.value = Cache.getFeedFromCache(feedType)
    }

    //region navigateToDetailedPostEvent
    private val _navigateToDetailedPostEvent = MutableLiveData<HabrPost>()
    val navigateToDetailedPostEvent: LiveData<HabrPost> = _navigateToDetailedPostEvent

    fun navigateToDetailedPost(habrPost: HabrPost) {
        _navigateToDetailedPostEvent.value = habrPost
    }

    fun onNavigateToDetailedPostEnded() {
        _navigateToDetailedPostEvent.value = null
    }
    //endregion
    
    //region navigateToBrowserEvent
    private val _navigateToBrowserEvent = MutableLiveData<String>()
    val navigateToBrowserEvent: LiveData<String> = _navigateToBrowserEvent
    
    fun navigateToBrowser(link: String) {
        _navigateToBrowserEvent.value = link
    }
    
    fun onNavigateToBrowserEnded() {
        _navigateToBrowserEvent.value = null
    }
    //endregion

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }
}
