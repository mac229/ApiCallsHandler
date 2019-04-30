package com.maciejkozlowski.apihandler.response

import com.maciejkozlowski.apihandler.errors.ApiError

sealed class ApiResponse<T> {

    class Success<T>(val data: T) : ApiResponse<T>()
    class Error<T>(val error: ApiError) : ApiResponse<T>()
}