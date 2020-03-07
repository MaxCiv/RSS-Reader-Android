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
    },
    BEST_MONTHLY(R.string.feed_type_best_monthly) {
        override suspend fun getHabrPosts(habrRssRepository: HabrRssRepository): Result<List<HabrPost>> {
            return habrRssRepository.getBestMonthly()
        }
    },
    BEST_YEARLY(R.string.feed_type_best_yearly) {
        override suspend fun getHabrPosts(habrRssRepository: HabrRssRepository): Result<List<HabrPost>> {
            return habrRssRepository.getBestYearly()
        }
    },
    ALL(R.string.feed_type_all) {
        override suspend fun getHabrPosts(habrRssRepository: HabrRssRepository): Result<List<HabrPost>> {
            return habrRssRepository.getAll()
        }
    },
    ALL_TOP_10(R.string.feed_type_all_top_10) {
        override suspend fun getHabrPosts(habrRssRepository: HabrRssRepository): Result<List<HabrPost>> {
            return habrRssRepository.getAllTop10()
        }
    },
    ALL_TOP_25(R.string.feed_type_all_top_25) {
        override suspend fun getHabrPosts(habrRssRepository: HabrRssRepository): Result<List<HabrPost>> {
            return habrRssRepository.getAllTop25()
        }
    },
    ALL_TOP_50(R.string.feed_type_all_top_50) {
        override suspend fun getHabrPosts(habrRssRepository: HabrRssRepository): Result<List<HabrPost>> {
            return habrRssRepository.getAllTop50()
        }
    },
    ALL_TOP_100(R.string.feed_type_all_top_100) {
        override suspend fun getHabrPosts(habrRssRepository: HabrRssRepository): Result<List<HabrPost>> {
            return habrRssRepository.getAllTop100()
        }
    }
    ;

    abstract suspend fun getHabrPosts(habrRssRepository: HabrRssRepository): Result<List<HabrPost>>
}
