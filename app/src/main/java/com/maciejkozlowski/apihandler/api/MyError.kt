package com.maciejkozlowski.apihandler.api

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

/**
 * Created by Maciej Kozłowski on 2019-04-29.
 */
@JsonClass(generateAdapter = true)
data class MyError(

    @Json(name = "message")
    val message: String
)