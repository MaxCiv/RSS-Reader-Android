package com.maxciv.rssreader.network

import com.maxciv.rssreader.model.rss.RssFeed
import retrofit2.Response
import retrofit2.http.GET

/**
 * @author maxim.oleynik
 * @since 07.03.2020
 */
interface HabrRssApi {

    @GET("best/daily")
    suspend fun getBestDaily(): Response<RssFeed>

    @GET("best/weekly")
    suspend fun getBestWeekly(): Response<RssFeed>

    @GET("best/monthly")
    suspend fun getBestMonthly(): Response<RssFeed>

    @GET("best/yearly")
    suspend fun getBestYearly(): Response<RssFeed>

    @GET("all/all")
    suspend fun getAll(): Response<RssFeed>

    @GET("all/top10")
    suspend fun getAllTop10(): Response<RssFeed>

    @GET("all/top25")
    suspend fun getAllTop25(): Response<RssFeed>

    @GET("all/top50")
    suspend fun getAllTop50(): Response<RssFeed>

    @GET("all/top100")
    suspend fun getAllTop100(): Response<RssFeed>
}
