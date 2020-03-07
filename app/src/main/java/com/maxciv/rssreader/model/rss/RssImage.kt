package com.maxciv.rssreader.model.rss

import org.simpleframework.xml.Element
import org.simpleframework.xml.Root

/**
 * @author maxim.oleynik
 * @since 07.03.2020
 */
@Root(name = "image", strict = false)
data class RssImage(

        @field:Element
        var title: String = "",

        @field:Element
        var url: String = "",

        @field:Element
        var link: String = ""
)
