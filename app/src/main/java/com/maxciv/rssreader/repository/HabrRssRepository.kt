package com.maxciv.rssreader.repository

import com.maxciv.rssreader.model.HabrPost
import com.maxciv.rssreader.model.rss.RssFeed
import com.maxciv.rssreader.model.rss.convertToHabrPosts
import com.maxciv.rssreader.network.HabrRssApi
import com.maxciv.rssreader.util.Result

/**
 * @author maxim.oleynik
 * @since 07.03.2020
 */
class HabrRssRepository(private val api: HabrRssApi) : BaseRepository() {

    suspend fun getBestDaily(): Result<List<HabrPost>> {
        return safeApiCall(converter = {it.convertToHabrPosts()}) {
            api.getBestDaily()
        }
    }

    suspend fun getBestWeekly(): Result<List<HabrPost>> {
        return safeApiCall(converter = {it.convertToHabrPosts()}) {
            api.getBestWeekly()
        }
    }
}
