package com.maxciv.rssreader.repository

import com.maxciv.rssreader.util.Result
import retrofit2.Response
import java.io.IOException

/**
 * @author maxim.oleynik
 * @since 07.03.2020
 */
open class BaseRepository {

    protected suspend fun <S : Any, D : Any> safeApiCall(
            errorMessage: String = "",
            converter: (S) -> D,
            call: suspend () -> Response<S>
    ): Result<D> {
        try {
            val response = call.invoke()
            if (response.isSuccessful) {
                return Result.Success(converter.invoke(response.body()!!))
            }

            return Result.Fail(
                    errorMessage,
                    IOException("Error occurred during safe Api call: ${response.errorBody()?.string()}")
            )
        } catch (e: IOException) {
            return Result.Fail(errorMessage, e)
        }
    }
}
