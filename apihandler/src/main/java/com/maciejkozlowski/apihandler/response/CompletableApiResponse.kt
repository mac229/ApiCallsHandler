package com.maciejkozlowski.apihandler.response

import com.maciejkozlowski.apihandler.errors.ApiError

sealed class CompletableApiResponse {

    object Complete : CompletableApiResponse()
    class Error(val error: ApiError) : CompletableApiResponse()
}