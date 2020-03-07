package com.maxciv.rssreader.model

import com.maxciv.rssreader.R
import com.maxciv.rssreader.repository.HabrRssRepository
import com.maxciv.rssreader.util.Result

/**
 * @author maxim.oleynik
 * @since 07.03.2020
 */
enum class FeedType(val feedTitleResId: Int) {

    BEST_DAILY(R.string.feed_type_best_daily) {
        override suspend fun getHabrPosts(habrRssRepository: HabrRssRepository): Result<List<HabrPost>> {
            return habrRssRepository.getBestDaily()
        }
    },
    BEST_WEEKLY(R.string.feed_type_best_weekly) {
        override suspend fun getHabrPosts(habrRssRepository: HabrRssRepository): Result<List<HabrPost>> {
            return habrRssRepository.getBestWeekly()
        }
    };

    abstract suspend fun getHabrPosts(habrRssRepository: HabrRssRepository): Result<List<HabrPost>>
}
