package com.maxciv.rssreader.model

/**
 * @author maxim.oleynik
 * @since 07.03.2020
 */
data class HabrPost(
        var channel: HabrChannel,
        var guid: String = "",
        var title: String = "",
        var description: String = "",
        var link: String = "",
        var pubDate: String = "",
        var creator: String = "",
        var categories: MutableList<String> = mutableListOf()
)
