package com.maxciv.rssreader.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.maxciv.rssreader.Cache
import com.maxciv.rssreader.R
import com.maxciv.rssreader.model.FeedType
import com.maxciv.rssreader.model.HabrFeed
import com.maxciv.rssreader.model.HabrPost
import com.maxciv.rssreader.network.ApiFactory
import com.maxciv.rssreader.repository.HabrRssRepository
import com.maxciv.rssreader.util.Result
import com.maxciv.rssreader.util.ToastHelper
import kotlinx.coroutines.*

/**
 * @author maxim.oleynik
 * @since 06.03.2020
 */
class PostsListViewModel : ViewModel() {

    private val viewModelJob: CompletableJob = SupervisorJob()
    private val viewModelScope: CoroutineScope = CoroutineScope(viewModelJob + Dispatchers.Main)
    private val repository: HabrRssRepository = HabrRssRepository(ApiFactory.habrRssApi)

    val toastHelper = ToastHelper()

    //region isLoading
    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private fun setLoadingStarted() {
        _isLoading.value = true
    }

    private fun postLoadingStopped() {
        _isLoading.postValue(false)
    }
    //endregion

    //region habrFeed
    private val _habrFeed = MutableLiveData<HabrFeed>()
    val habrFeed: LiveData<HabrFeed> = _habrFeed

    fun setNewHabrFeed(habrFeed: HabrFeed?) {
        _habrFeed.value = habrFeed
    }

    private fun postNewHabrFeed(habrFeed: HabrFeed?) {
        _habrFeed.postValue(habrFeed)
    }

    private fun postCurrentHabrFeedAgain() {
        val some = if (_habrFeed.value == null) HabrFeed() else _habrFeed.value
        _habrFeed.postValue(some)
    }
    //endregion

    fun loadHabrFeed(feedType: FeedType, showErrorMessageOnFail: Boolean = false) {
        if (isLoading.value == true) return

        setLoadingStarted()
        viewModelScope.launch(Dispatchers.IO) {
            when (val habrFeed = feedType.getHabrFeed(repository)) {
                is Result.Success -> {
                    Cache.addFeedToCache(feedType, habrFeed.data)
                    postNewHabrFeed(habrFeed.data)
                }
                is Result.Fail -> {
                    if (showErrorMessageOnFail) {
                        toastHelper.show(R.string.failed_to_load_new_posts)
                    }
                    postCurrentHabrFeedAgain()
                }
            }
            postLoadingStopped()
        }
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
