package com.maxciv.rssreader.viewmodels

import android.os.Bundle
import androidx.lifecycle.ViewModel
import com.maxciv.rssreader.network.ApiFactory
import com.maxciv.rssreader.repository.HabrRssRepository
import com.maxciv.rssreader.util.Result
import kotlinx.coroutines.*
import timber.log.Timber

/**
 * @author maxim.oleynik
 * @since 06.03.2020
 */
private val KEY_CLICK_COUNT = "${PostsListViewModel::class.java}.KEY_CLICK_COUNT"

class PostsListViewModel : ViewModel() {

    private val viewModelJob: CompletableJob = SupervisorJob()
    private val viewModelScope: CoroutineScope = CoroutineScope(viewModelJob + Dispatchers.Main)
    private val repository : HabrRssRepository = HabrRssRepository(ApiFactory.getHabrRssApi())

    var clickCount: Int = 0

    fun loadRss() {
        viewModelScope.launch(Dispatchers.IO) {
            when (val bestDaily = repository.getBestDaily()) {
                is Result.Success -> {
                    bestDaily.data
                }
                is Result.Error -> {
                }
            }
            Timber.e("")

        }
    }

    fun saveStateToBundle(outState: Bundle) {
        outState.putInt(KEY_CLICK_COUNT, clickCount)
    }

    fun restoreStateFromBundle(savedInstanceState: Bundle) {
        clickCount = savedInstanceState.getInt(KEY_CLICK_COUNT)
    }

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }
}
