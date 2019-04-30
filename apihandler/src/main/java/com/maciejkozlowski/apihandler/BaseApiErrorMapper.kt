package com.maciejkozlowski.apihandler

import com.maciejkozlowski.apihandler.errors.*
import com.maciejkozlowski.apihandler.response.ApiResponse
import com.maciejkozlowski.apihandler.response.CompletableApiResponse
import okhttp3.ResponseBody
import retrofit2.HttpException
import java.io.IOException
import java.net.HttpURLConnection

/**
 * Created by Maciej Kozłowski on 15/03/2019.
 */

abstract class BaseApiErrorMapper<T>(private val parser: Parser<T>) {

    fun <T> mapToApiResponse(throwable: Throwable): ApiResponse<T> {
        return ApiResponse.Error(mapToApiError(throwable))
    }

    fun mapToCompletableApiResponse(throwable: Throwable): CompletableApiResponse {
        return CompletableApiResponse.Error(mapToApiError(throwable))
    }

    fun mapToApiError(throwable: Throwable): ApiError {
        return when (throwable) {
            is HttpException -> handleHttpException(throwable)
            is IOException   -> ConnectionError
            else             -> UnknownError(throwable)
        }
    }

    private fun handleHttpException(exception: HttpException): ApiError {
        return when (exception.code()) {
            HttpURLConnection.HTTP_UNAUTHORIZED -> UnauthorizedError
            in (400 until 500)                  -> parseBadRequest(exception)
            in (500 until 600)                  -> ServerError
            else                                -> UnknownError(exception)
        }
    }

    private fun parseBadRequest(exception: HttpException): ApiError {
        val errors = parseErrorBodySafely(exception.response().errorBody())
        return if (errors != null && isErrorValid(errors)) {
            createRequestError(errors)
        } else {
            UnknownError(exception)
        }
    }

    protected open fun <T> isErrorValid(errors: T) = true

    protected abstract fun createRequestError(errors: T): ApiError

    private fun parseErrorBodySafely(errorBody: ResponseBody?): T? {
        return errorBody?.string()?.let(this::parseErrors)
    }

    private fun parseErrors(json: String): T? {
        return try {
            parser.parseFromJson(json)
        } catch (exception: Exception) {
            logException(exception)
            null
        }
    }

    protected open fun logException(exception: Exception) = Unit
}