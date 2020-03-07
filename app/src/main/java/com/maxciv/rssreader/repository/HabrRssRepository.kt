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

    suspend fun getBestMonthly(): Result<List<HabrPost>> {
        return safeApiCall(converter = {it.convertToHabrPosts()}) {
            api.getBestMonthly()
        }
    }

    suspend fun getBestYearly(): Result<List<HabrPost>> {
        return safeApiCall(converter = {it.convertToHabrPosts()}) {
            api.getBestYearly()
        }
    }

    suspend fun getAll(): Result<List<HabrPost>> {
        return safeApiCall(converter = {it.convertToHabrPosts()}) {
            api.getAll()
        }
    }

    suspend fun getAllTop10(): Result<List<HabrPost>> {
        return safeApiCall(converter = {it.convertToHabrPosts()}) {
            api.getAllTop10()
        }
    }

    suspend fun getAllTop25(): Result<List<HabrPost>> {
        return safeApiCall(converter = {it.convertToHabrPosts()}) {
            api.getAllTop25()
        }
    }

    suspend fun getAllTop50(): Result<List<HabrPost>> {
        return safeApiCall(converter = {it.convertToHabrPosts()}) {
            api.getAllTop50()
        }
    }

    suspend fun getAllTop100(): Result<List<HabrPost>> {
        return safeApiCall(converter = {it.convertToHabrPosts()}) {
            api.getAllTop100()
        }
    }
}
