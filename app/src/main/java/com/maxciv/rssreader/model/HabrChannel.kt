package com.maxciv.rssreader.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

/**
 * @author maxim.oleynik
 * @since 07.03.2020
 */
@Parcelize
data class HabrChannel(
        var title: String = "",
        var link: String = "",
        var imageTitle: String = "",
        var imageUrl: String = "",
        var imageLink: String = ""
) : Parcelable
