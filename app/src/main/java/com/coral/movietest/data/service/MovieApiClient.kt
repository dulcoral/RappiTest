package com.coral.movietest.data.service

import com.coral.movietest.BuildConfig
import com.coral.movietest.util.Constants
import com.coral.movietest.util.LiveDataCallAdapterFactory
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.IOException
import javax.inject.Singleton

@Singleton
class MovieApiClient private constructor() {
    val movieService: MovieApiService

    init {
        val logging = HttpLoggingInterceptor()
        logging.level = HttpLoggingInterceptor.Level.BASIC
        val client = OkHttpClient.Builder()
            .addInterceptor(logging)
            .addInterceptor(AuthInterceptor())
            .build()
        val retrofit = Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(LiveDataCallAdapterFactory())
            .build()
        movieService = retrofit.create(MovieApiService::class.java)
    }

    private class AuthInterceptor : Interceptor {
        @Throws(IOException::class)
        override fun intercept(chain: Interceptor.Chain): Response {

            var request = chain.request()

            val url = request.url().newBuilder()
                .addQueryParameter("api_key", BuildConfig.ApiKey)
                .build()

            request = request.newBuilder().url(url).build()
            return chain.proceed(request)
        }
    }

    companion object {
        val instance: Lazy<MovieApiClient> = lazy { MovieApiClient() }
    }
}
