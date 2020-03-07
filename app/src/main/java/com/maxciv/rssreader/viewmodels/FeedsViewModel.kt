package com.maxciv.rssreader.viewmodels

import androidx.lifecycle.ViewModel

/**
 * @author maxim.oleynik
 * @since 06.03.2020
 */
class FeedsViewModel : ViewModel() {

    val items: List<Int> = (1..5).toList()
}
