package com.maxciv.rssreader.model.rss

import org.simpleframework.xml.Element
import org.simpleframework.xml.ElementList
import org.simpleframework.xml.Root

/**
 * @author maxim.oleynik
 * @since 07.03.2020
 */
@Root(name = "channel", strict = false)
data class RssChannel(

        @field:Element
        var title: String = "",

        @field:Element
        var link: String = "",

        @field:Element
        var image: RssImage = RssImage(),

        @field:ElementList(entry = "item", inline = true, required = false)
        var items: MutableList<RssItem> = mutableListOf()
)
