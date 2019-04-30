package com.maciejkozlowski.apihandler

import android.util.Log
import com.maciejkozlowski.apihandler.api.RequestError
import com.maciejkozlowski.apihandler.di.Module
import com.maciejkozlowski.apihandler.errors.ApiError
import com.maciejkozlowski.apihandler.errors.ConnectionError
import com.maciejkozlowski.apihandler.errors.ServerError
import com.maciejkozlowski.apihandler.errors.UnauthorizedError
import com.maciejkozlowski.apihandler.response.ApiResponse
import com.maciejkozlowski.apihandler.response.CompletableApiResponse
import io.reactivex.Single
import io.reactivex.disposables.Disposable

/**
 * Created by Maciej Kozłowski on 2019-04-29.
 */
class MainRepository {

    private val apiErrorMapper = Module.apiErrorMapper
    private val service = Module.service

    fun getList(): Disposable {
        return service
            .getList()
            .map<ApiResponse<List<Any>>> { ApiResponse.Success(it) }
            .onErrorReturn { apiErrorMapper.mapToApiResponse(it) }
            .schedule()
            .subscribeApiCall(this::onSuccess, this::onError)
    }

    private fun onSuccess(list: List<Any>) {
        Log.d(TAG, "Success size: ${list.size}")
    }

    fun getListCompletable(): Disposable {
        return service
            .getListCompletable()
            .toSingleDefault<CompletableApiResponse>(CompletableApiResponse.Complete)
            .onErrorReturn { apiErrorMapper.mapToCompletableApiResponse(it) }
            .schedule()
            .subscribeApiCall(this::onComplete, this::onError)
    }

    private fun onComplete() {
        Log.d(TAG, "Completed")
    }

    private fun onError(apiError: ApiError) {
        when (apiError) {
            ConnectionError   -> Log.d(TAG, "ConnectionError")
            ServerError       -> Log.d(TAG, "ServerError")
            is RequestError   -> Log.d(TAG, "ApiRequestError")
            UnauthorizedError -> Log.d(TAG, "UnauthorizedError")
            is UnknownError   -> Log.d(TAG, "UnknownError")
            else              -> throw IllegalStateException("Unhandled type")
        }
    }

    private fun <T> Single<T>.schedule(): Single<T> {
        return subscribeOn(io.reactivex.schedulers.Schedulers.io())
            .observeOn(io.reactivex.android.schedulers.AndroidSchedulers.mainThread())
    }

    companion object {
        private const val TAG = "###hash"
    }
}