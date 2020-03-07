package com.maxciv.rssreader.network

import retrofit2.Retrofit
import retrofit2.converter.simplexml.SimpleXmlConverterFactory

/**
 * @author maxim.oleynik
 * @since 07.03.2020
 */
object ApiFactory {

    private const val HABR_RSS_BASE_URL = "https://habr.com/ru/rss/"

    private fun retrofit(): Retrofit {
        return Retrofit.Builder()
                .baseUrl(HABR_RSS_BASE_URL)
                .addConverterFactory(SimpleXmlConverterFactory.create())
                .build()
    }

    fun getHabrRssApi(): HabrRssApi {
        return retrofit().create(HabrRssApi::class.java)
    }
}
