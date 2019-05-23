package com.coral.movietest.util

import androidx.lifecycle.LiveData
import com.coral.movietest.data.service.MovieApiResponse
import retrofit2.Call
import retrofit2.CallAdapter
import retrofit2.Callback
import retrofit2.Response
import java.lang.reflect.Type
import java.util.concurrent.atomic.AtomicBoolean

class LiveDataCallAdapter<R>(private val responseType: Type) :
    CallAdapter<R, LiveData<MovieApiResponse<R>>> {

    override fun responseType() = responseType


    override fun adapt(call: Call<R>): LiveData<MovieApiResponse<R>> {
        return object : LiveData<MovieApiResponse<R>>() {
            private var started = AtomicBoolean(false)

            override fun onActive() {
                super.onActive()
                if (started.compareAndSet(false, true)) {
                    call.enqueue(object : Callback<R> {
                        override fun onResponse(call: Call<R>, response: Response<R>) {
                            postValue(MovieApiResponse(response))
                        }

                        override fun onFailure(call: Call<R>, throwable: Throwable) {
                            postValue(MovieApiResponse(throwable))
                        }
                    })
                }
            }
        }
    }
}