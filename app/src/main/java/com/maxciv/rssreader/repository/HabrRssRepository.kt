package com.maxciv.rssreader.repository

import com.maxciv.rssreader.model.HabrFeed
import com.maxciv.rssreader.model.HabrPost
import com.maxciv.rssreader.model.rss.convertToHabrFeed
import com.maxciv.rssreader.network.HabrRssApi
import com.maxciv.rssreader.util.Result

/**
 * @author maxim.oleynik
 * @since 07.03.2020
 */
class HabrRssRepository(private val api: HabrRssApi) : BaseRepository() {

    suspend fun getBestDaily(): Result<HabrFeed> {
        return safeApiCall(converter = {it.convertToHabrFeed()}) {
            api.getBestDaily()
        }
    }

    suspend fun getBestWeekly(): Result<HabrFeed> {
        return safeApiCall(converter = {it.convertToHabrFeed()}) {
            api.getBestWeekly()
        }
    }

    suspend fun getBestMonthly(): Result<HabrFeed> {
        return safeApiCall(converter = {it.convertToHabrFeed()}) {
            api.getBestMonthly()
        }
    }

    suspend fun getBestYearly(): Result<HabrFeed> {
        return safeApiCall(converter = {it.convertToHabrFeed()}) {
            api.getBestYearly()
        }
    }

    suspend fun getAll(): Result<HabrFeed> {
        return safeApiCall(converter = {it.convertToHabrFeed()}) {
            api.getAll()
        }
    }

    suspend fun getAllTop10(): Result<HabrFeed> {
        return safeApiCall(converter = {it.convertToHabrFeed()}) {
            api.getAllTop10()
        }
    }

    suspend fun getAllTop25(): Result<HabrFeed> {
        return safeApiCall(converter = {it.convertToHabrFeed()}) {
            api.getAllTop25()
        }
    }

    suspend fun getAllTop50(): Result<HabrFeed> {
        return safeApiCall(converter = {it.convertToHabrFeed()}) {
            api.getAllTop50()
        }
    }

    suspend fun getAllTop100(): Result<HabrFeed> {
        return safeApiCall(converter = {it.convertToHabrFeed()}) {
            api.getAllTop100()
        }
    }
}
