package com.coral.movietest.data.service

import retrofit2.Response
import java.io.IOException

class MovieApiResponse<T> {
    private var code: Int
    private var body: T?
    private var error: Throwable?


    constructor(error: Throwable?) {
        this.code = 500
        this.body = null
        this.error = error
    }

    constructor(response: Response<T>) {
        code = response.code()
        if (response.isSuccessful) {
            body = response.body()
            error = null
        } else {
            var message: String? = null
            if (response.errorBody() != null) {
                try {
                    message = response.errorBody()!!.string()
                } catch (ignored: IOException) {
                }

            }
            if (message == null || message.trim { it <= ' ' }.isEmpty()) {
                message = response.message()
            }
            error = IOException(message)
            body = null
        }
    }

    fun isSuccessful(): Boolean {
        return code in 200..299
    }


    fun getCode(): Int {
        return code
    }

    fun getBody(): T? {
        return body
    }

    fun getError(): Throwable? {
        return error
    }
}