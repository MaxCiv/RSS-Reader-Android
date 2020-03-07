package com.maxciv.rssreader.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.maxciv.rssreader.Cache
import com.maxciv.rssreader.model.FeedType
import com.maxciv.rssreader.model.HabrFeed
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
                    _habrFeed.postValue(HabrFeed())
                }
            }
        }
    }

    fun getHabrFeedFromCache(feedType: FeedType) {
        _habrFeed.value = Cache.getFeedFromCache(feedType)
    }

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }
}
