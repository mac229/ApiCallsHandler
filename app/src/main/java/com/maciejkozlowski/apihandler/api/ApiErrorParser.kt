package com.maciejkozlowski.apihandler.api

import com.maciejkozlowski.apihandler.Parser
import com.squareup.moshi.JsonAdapter

/**
 * Created by Maciej Kozłowski on 2019-04-30.
 */

class ApiErrorParser(private val jsonAdapter: JsonAdapter<MyError>) : Parser<MyError> {

    override fun parseFromJson(json: String): MyError? {
        return jsonAdapter.fromJson(json)
    }
}