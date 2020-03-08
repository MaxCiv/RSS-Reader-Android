package com.maxciv.rssreader.util

import timber.log.Timber
import java.text.SimpleDateFormat
import java.util.*

/**
 * @author maxim.oleynik
 * @since 07.03.2020
 */
val HABR_DATE_FORMAT = SimpleDateFormat("E, dd MMM yyyy HH:mm:ss z", Locale.ENGLISH)

fun parseHabrDate(date: String): Long {
    return try {
        HABR_DATE_FORMAT.parse(date)?.time ?: System.currentTimeMillis()
    } catch (e: Exception) {
        Timber.e(e)
        System.currentTimeMillis()
    }
}

inline fun <reified T : Enum<T>> enumValueOf(name: String, defaultValue: T): T {
    return try {
        enumValues<T>().first { it.name == name }
    } catch (e: NoSuchElementException) {
        defaultValue
    }
}
