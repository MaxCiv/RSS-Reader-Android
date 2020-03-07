package com.maxciv.rssreader.model

import android.os.Parcelable
import androidx.annotation.Keep
import com.maxciv.rssreader.util.parseHabrDate
import kotlinx.android.parcel.Parcelize

/**
 * @author maxim.oleynik
 * @since 07.03.2020
 */
@Keep
@Parcelize
data class HabrPost(
        var guid: String = "",
        var title: String = "",
        var description: String = "",
        var link: String = "",
        var pubDate: String = "",
        var creator: String = "",
        var categories: MutableList<String> = mutableListOf()
) : Parcelable {

    fun getPubDateMillis(): Long {
        return parseHabrDate(pubDate)
    }
}
