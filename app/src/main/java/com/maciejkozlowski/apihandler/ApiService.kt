package com.maciejkozlowski.apihandler

import io.reactivex.Completable
import io.reactivex.Single
import retrofit2.http.GET

/**
 * Created by Maciej Kozłowski on 2019-04-29.
 */

interface ApiService {

    @GET("56d6e71d130000c20e5438ad")
    fun getList() : Single<List<Any>>

    @GET("56d6e71d130000c20e5438ad")
    fun getListCompletable() : Completable
}