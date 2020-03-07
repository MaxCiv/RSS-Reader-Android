package com.maxciv.rssreader.model.rss

import com.maxciv.rssreader.model.HabrChannel
import com.maxciv.rssreader.model.HabrFeed
import com.maxciv.rssreader.model.HabrPost
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

fun RssFeed.convertToHabrFeed(): HabrFeed {
    return HabrFeed(
            HabrChannel(
                    channel.title,
                    channel.link,
                    channel.image.title,
                    channel.image.url,
                    channel.image.link
            ),
            channel.items.map { rssItem ->
                HabrPost(
                        rssItem.guid,
                        rssItem.title,
                        rssItem.description,
                        rssItem.link,
                        rssItem.pubDate,
                        rssItem.creator,
                        rssItem.categories
                )
            }.toMutableList()
    )
}
