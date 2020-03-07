package com.maxciv.rssreader.model.rss

import org.simpleframework.xml.Element
import org.simpleframework.xml.ElementList
import org.simpleframework.xml.Root

/**
 * @author maxim.oleynik
 * @since 07.03.2020
 */
@Root(name = "item", strict = false)
data class RssItem(

        @field:Element
        var guid: String = "",

        @field:Element
        var title: String = "",

        @field:Element
        var description: String = "",

        @field:Element
        var link: String = "",

        @field:Element
        var pubDate: String = "",

        @field:Element(name = "creator")
        var creator: String = "",

        @field:ElementList(entry = "category", inline = true, required = false)
        var categories: MutableList<String> = mutableListOf()
)
