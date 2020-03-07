package com.maxciv.rssreader.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

/**
 * @author maxim.oleynik
 * @since 07.03.2020
 */
@Parcelize
data class HabrFeed(
        var channel: HabrChannel = HabrChannel(),
        var posts: MutableList<HabrPost> = mutableListOf()
) : Parcelable
