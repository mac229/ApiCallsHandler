package com.maciejkozlowski.apihandler.api

import com.maciejkozlowski.apihandler.BaseApiErrorMapper

/**
 * Created by Maciej Kozłowski on 2019-04-29.
 */

class ApiErrorMapper(parser: ApiErrorParser): BaseApiErrorMapper<MyError>(parser) {

    override fun createRequestError(errors: MyError): RequestError {
        return RequestError(errors)
    }

}