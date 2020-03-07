package com.maxciv.rssreader.adapters

import android.text.format.DateUtils
import android.widget.TextView
import androidx.core.text.HtmlCompat
import androidx.databinding.BindingAdapter
import com.maxciv.rssreader.model.HabrPost

/**
 * @author maxim.oleynik
 * @since 07.03.2020
 */
@BindingAdapter("postDescription")
fun TextView.setPostDescription(habrPost: HabrPost?) {
    text = HtmlCompat.fromHtml(
                    habrPost?.description ?: "",
                    HtmlCompat.FROM_HTML_MODE_LEGACY
            ).toString()
            .replace("￼", "")
            .lines()
            .filter { !it.contains("→") }
            .joinToString(separator = "\n")
            .trim()
            .replace("""\n{4,}""".toRegex(), "\n\n\n")
}

@BindingAdapter("pubDate")
fun TextView.setPubDate(habrPost: HabrPost?) {
    text = DateUtils.getRelativeTimeSpanString(
            habrPost?.getPubDateMillis() ?: System.currentTimeMillis(),
            System.currentTimeMillis(),
            0
    )
}
