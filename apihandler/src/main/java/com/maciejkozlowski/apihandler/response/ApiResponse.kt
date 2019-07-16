package com.maciejkozlowski.apihandler.response

import com.maciejkozlowski.apihandler.errors.ApiError

sealed class ApiResponse<T> {

    data class Success<T>(val data: T) : ApiResponse<T>()
    data class Error<T>(val error: ApiError) : ApiResponse<T>()
}