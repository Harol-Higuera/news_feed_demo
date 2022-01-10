package com.harol.newsfeed.network

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

object NewsApiClient {

    private const val BASE_URL = "https://newsapi.org/v2/"
    private const val API_KEY = "715861e4b4874400a164e9d7bca63c0c"

    private val moshi = Moshi.Builder()
        .add(KotlinJsonAdapterFactory())
        .build()

    private val loggerInterceptor = run {
        val httpLoggingInterceptor = HttpLoggingInterceptor()
        httpLoggingInterceptor.apply {
            httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
        }
    }

    private val authInterceptor = Interceptor { chain ->

        val originalRequest = chain.request()
        val originalHttpUrl = originalRequest.url

        val newUrl = originalHttpUrl
            .newBuilder()
            .addQueryParameter("apiKey", API_KEY)
            .build()

        val request = originalRequest
            .newBuilder()
            .url(newUrl)
            .build()

        chain.proceed(request = request)
    }


    private val client = OkHttpClient.Builder()
        .addInterceptor(loggerInterceptor)
        .addInterceptor(authInterceptor)
        .build()

    private val retrofit = Retrofit.Builder()
        .client(client)
        .addConverterFactory(MoshiConverterFactory.create(moshi))
        .baseUrl(BASE_URL)
        .build()

    val RETROFIT_API_SERVICE: NewsApiService by lazy {
        retrofit.create(NewsApiService::class.java)
    }
}