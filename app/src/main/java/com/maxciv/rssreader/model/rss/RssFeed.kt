package com.maxciv.rssreader.model.rss

import com.maxciv.rssreader.model.HabrChannel
import com.maxciv.rssreader.model.HabrPost
import com.maxciv.rssreader.util.parseHabrDate
import org.simpleframework.xml.Element
import org.simpleframework.xml.Root

/**
 * @author maxim.oleynik
 * @since 07.03.2020
 */
@Root(name = "rss", strict = false)
data class RssFeed(

        @field:Element
        var channel: RssChannel = RssChannel()
)

fun RssFeed.convertToHabrPosts(): List<HabrPost> {
    return channel.items.map { rssItem ->
        HabrPost(
                HabrChannel(
                        channel.title,
                        channel.link,
                        channel.image.title,
                        channel.image.url,
                        channel.image.link
                ),
                rssItem.guid,
                rssItem.title,
                rssItem.description,
                rssItem.link,
                parseHabrDate(rssItem.pubDate),
                rssItem.creator,
                rssItem.categories
        )
    }
}
