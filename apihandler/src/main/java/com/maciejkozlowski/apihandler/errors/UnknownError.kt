package com.maciejkozlowski.apihandler.errors

data class UnknownError(val throwable: Throwable) : ApiError
