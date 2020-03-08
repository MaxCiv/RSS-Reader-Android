package com.maxciv.rssreader

import android.content.Context
import android.content.SharedPreferences
import com.maxciv.rssreader.model.FeedType
import com.maxciv.rssreader.util.enumValueOf

/**
 * @author maxim.oleynik
 * @since 08.03.2020
 */
class AppPreferences(context: Context) {

    private val prefs: SharedPreferences = context.getSharedPreferences(PREFS_FILENAME, Context.MODE_PRIVATE)

    var currentFeedType: FeedType
        get() = enumValueOf((prefs.getString(KEY_CURRENT_FEED_TYPE, FeedType.BEST_DAILY.name)!!), FeedType.BEST_DAILY)
        set(value) = prefs.edit().putString(KEY_CURRENT_FEED_TYPE, value.name).apply()


    companion object {
        private const val PREFS_FILENAME = "com.maxciv.rssreader.preferences"
        private const val KEY_CURRENT_FEED_TYPE = "current_feed_type"
    }
}
