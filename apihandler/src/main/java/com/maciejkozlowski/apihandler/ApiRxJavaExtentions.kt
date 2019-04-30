package com.maciejkozlowski.apihandler

import com.maciejkozlowski.apihandler.errors.ApiError
import com.maciejkozlowski.apihandler.response.ApiResponse
import com.maciejkozlowski.apihandler.response.CompletableApiResponse
import io.reactivex.Single
import io.reactivex.disposables.Disposable

/**
 * Created by Maciej Kozłowski on 2019-04-30.
 */

fun <T> Single<ApiResponse<T>>.subscribeApiCall(onSuccess: (T) -> Unit, onError: (ApiError) -> Unit): Disposable {
    return subscribe { data -> handleResult(data, onSuccess, onError) }
}

private fun <T> handleResult(response: ApiResponse<T>, onSuccess: (T) -> Unit, onError: (ApiError) -> Unit) {
    when (response) {
        is ApiResponse.Success<T> -> onSuccess(response.data)
        is ApiResponse.Error<T>   -> onError(response.error)
    }
}

fun Single<CompletableApiResponse>.subscribeApiCall(onComplete: () -> Unit, onError: (ApiError) -> Unit): Disposable {
    return subscribe { data -> handleResult(data, onComplete, onError) }
}

private fun handleResult(response: CompletableApiResponse, onComplete: () -> Unit, onError: (ApiError) -> Unit) {
    when (response) {
        is CompletableApiResponse.Complete -> onComplete()
        is CompletableApiResponse.Error    -> onError(response.error)
    }
}
