package com.maxciv.rssreader.util

/**
 * @author maxim.oleynik
 * @since 07.03.2020
 */
sealed class Result<out T: Any> {
    data class Success<out T : Any>(val data: T) : Result<T>()
    data class Fail(val message: String, val exception: Exception) : Result<Nothing>()
}
