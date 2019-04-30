package com.maciejkozlowski.apihandler.api

import com.maciejkozlowski.apihandler.errors.ApiError

data class RequestError(val error: MyError) : ApiError
