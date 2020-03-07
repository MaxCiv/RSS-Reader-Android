package com.maxciv.rssreader.viewmodels

import android.os.Bundle
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.maxciv.rssreader.model.FeedType
import com.maxciv.rssreader.model.HabrPost
import com.maxciv.rssreader.network.ApiFactory
import com.maxciv.rssreader.repository.HabrRssRepository
import com.maxciv.rssreader.util.Result
import kotlinx.coroutines.*

/**
 * @author maxim.oleynik
 * @since 06.03.2020
 */
private val KEY_POSTS = "${PostsListViewModel::class.java}.KEY_POSTS"

class PostsListViewModel : ViewModel() {

    private val viewModelJob: CompletableJob = SupervisorJob()
    private val viewModelScope: CoroutineScope = CoroutineScope(viewModelJob + Dispatchers.Main)
    private val repository: HabrRssRepository = HabrRssRepository(ApiFactory.getHabrRssApi())

    private val _posts = MutableLiveData<List<HabrPost>>()
    val posts: LiveData<List<HabrPost>> = _posts

    fun loadHabrPosts(feedType: FeedType) {
        viewModelScope.launch(Dispatchers.IO) {
            when (val habrPosts = feedType.getHabrPosts(repository)) {
                is Result.Success -> {
                    _posts.postValue(habrPosts.data)
                }
                is Result.Error -> {
                    _posts.postValue(listOf())
                }
            }
        }
    }

    fun saveStateToBundle(outState: Bundle) {
        outState.putParcelableArrayList(KEY_POSTS, ArrayList(posts.value ?: listOf()))
    }

    fun restoreStateFromBundle(savedInstanceState: Bundle) {
        _posts.value = savedInstanceState.getParcelableArrayList(KEY_POSTS) ?: listOf()
    }

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }
}
