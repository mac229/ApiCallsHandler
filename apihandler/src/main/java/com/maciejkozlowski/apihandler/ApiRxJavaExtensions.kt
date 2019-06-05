package com.maciejkozlowski.apihandler

import com.maciejkozlowski.apihandler.errors.ApiError
import com.maciejkozlowski.apihandler.response.ApiResponse
import com.maciejkozlowski.apihandler.response.CompletableApiResponse
import io.reactivex.Completable
import io.reactivex.Single
import io.reactivex.disposables.Disposable

/**
 * Created by Maciej Kozłowski on 2019-04-30.
 */

fun <T> Single<ApiResponse<T>>.subscribeApiCall(onSuccess: (T) -> Unit, onError: (ApiError) -> Unit): Disposable {
    return subscribe { data -> handleResult(data, onSuccess, onError) }
}

fun <T> handleResult(response: ApiResponse<T>, onSuccess: (T) -> Unit, onError: (ApiError) -> Unit) {
    when (response) {
        is ApiResponse.Success<T> -> onSuccess(response.data)
        is ApiResponse.Error<T>   -> onError(response.error)
    }
}

fun Single<CompletableApiResponse>.subscribeApiCall(onComplete: () -> Unit, onError: (ApiError) -> Unit): Disposable {
    return subscribe { data -> handleResult(data, onComplete, onError) }
}

fun handleResult(response: CompletableApiResponse, onComplete: () -> Unit, onError: (ApiError) -> Unit) {
    when (response) {
        is CompletableApiResponse.Complete -> onComplete()
        is CompletableApiResponse.Error    -> onError(response.error)
    }
}

fun <T> Single<T>.mapToSuccessApiResponse(): Single<ApiResponse<T>> {
    return map<ApiResponse<T>> { ApiResponse.Success(it) }
}

fun Completable.mapToCompleteApiResponse(): Single<CompletableApiResponse> {
    return toSingle<CompletableApiResponse> { CompletableApiResponse.Complete }
}

fun <T> Single<T>.mapToCompleteApiResponse(): Single<CompletableApiResponse> {
    return map<CompletableApiResponse> { CompletableApiResponse.Complete }
}

fun <T> Single<T>.onErrorReturnApiResponse(mapToApiResponse: (Throwable) -> T): Single<T> {
    return onErrorReturn { mapToApiResponse(it) }
}
