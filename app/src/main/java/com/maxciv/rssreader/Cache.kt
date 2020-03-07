package com.maxciv.rssreader

import com.maxciv.rssreader.model.FeedType
import com.maxciv.rssreader.model.HabrFeed

/**
 * @author maxim.oleynik
 * @since 07.03.2020
 */
object Cache {

    private val feeds: MutableMap<FeedType, HabrFeed> = mutableMapOf()

    fun addFeedToCache(feedType: FeedType, habrFeed: HabrFeed) {
        feeds[feedType] = habrFeed
    }

    fun getFeedFromCache(feedType: FeedType): HabrFeed? {
        return feeds[feedType]
    }
}
