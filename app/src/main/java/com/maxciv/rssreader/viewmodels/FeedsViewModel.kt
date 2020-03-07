package com.maxciv.rssreader.viewmodels

import androidx.lifecycle.ViewModel
import com.maxciv.rssreader.model.FeedType

/**
 * @author maxim.oleynik
 * @since 06.03.2020
 */
class FeedsViewModel : ViewModel() {

    val feedTypes: List<FeedType> = FeedType.values().toList()
}
