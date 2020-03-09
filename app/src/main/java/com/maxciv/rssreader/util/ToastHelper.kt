package com.maxciv.rssreader.util

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

/**
 * @author maxim.oleynik
 * @since 09.03.2020
 */
class ToastHelper {

    private val _showToastEvent = MutableLiveData<Int>()
    val showToastEvent: LiveData<Int> = _showToastEvent

    fun show(messageResId: Int) {
        _showToastEvent.postValue(messageResId)
    }

    fun onShowEnded() {
        _showToastEvent.value = null
    }
}
