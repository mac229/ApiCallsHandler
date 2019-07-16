package com.maciejkozlowski.apihandler.response

import com.maciejkozlowski.apihandler.errors.ApiError

sealed class CompletableApiResponse {

    object Complete : CompletableApiResponse()
    data class Error(val error: ApiError) : CompletableApiResponse()
}