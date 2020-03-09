package com.maxciv.rssreader.viewmodels

import android.text.style.ImageSpan
import androidx.core.text.HtmlCompat
import androidx.core.text.getSpans
import androidx.lifecycle.ViewModel
import com.maxciv.rssreader.model.HabrPost

/**
 * @author maxim.oleynik
 * @since 06.03.2020
 */
class DetailedPostViewModel : ViewModel() {

    var habrPost = HabrPost()
        private set

    var imageUrl: String? = null
        private set

    fun setHabrPostAndParseImageUrl(newHabrPost: HabrPost) {
        habrPost = newHabrPost
        imageUrl = HtmlCompat.fromHtml(habrPost.description, HtmlCompat.FROM_HTML_MODE_LEGACY)
                .getSpans<ImageSpan>()
                .map { it.source }
                .firstOrNull()
    }
}
