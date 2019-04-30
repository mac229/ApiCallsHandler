package com.maciejkozlowski.apihandler.di

import com.maciejkozlowski.apihandler.ApiService
import com.maciejkozlowski.apihandler.api.ApiErrorMapper
import com.maciejkozlowski.apihandler.api.ApiErrorParser
import com.maciejkozlowski.apihandler.api.MyError
import com.squareup.moshi.Moshi
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.moshi.MoshiConverterFactory

/**
 * Created by Maciej Kozłowski on 2019-04-30.
 */
object Module {

    private val moshi = Moshi.Builder().build()
    private val parser = ApiErrorParser(moshi.adapter(MyError::class.java))

    val apiErrorMapper = ApiErrorMapper(parser)
    val service: ApiService

    init {
        val retrofit = Retrofit
            .Builder()
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .baseUrl("http://www.mocky.io/v2/")
            .build()

        service = retrofit.create(ApiService::class.java)
    }
}