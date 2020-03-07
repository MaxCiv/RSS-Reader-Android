package com.maxciv.rssreader.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

/**
 * @author maxim.oleynik
 * @since 07.03.2020
 */
@Parcelize
data class HabrPost(
        var channel: HabrChannel,
        var guid: String = "",
        var title: String = "",
        var description: String = "",
        var link: String = "",
        var pubDate: Long = System.currentTimeMillis(),
        var creator: String = "",
        var categories: MutableList<String> = mutableListOf()
) : Parcelable
