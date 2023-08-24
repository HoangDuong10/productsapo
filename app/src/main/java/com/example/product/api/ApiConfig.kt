package com.example.product.api

import hu.akarnokd.rxjava3.retrofit.RxJava3CallAdapterFactory
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object ApiConfig {
    val okHttpClient: OkHttpClient =  OkHttpClient.Builder()
               .addInterceptor { chain ->
                val original = chain.request()
                val requestBuilder = original.newBuilder()
                    .header("X-Sapo-SessionId", "0a7d5c7ea94f78da201c379786eaa091")
                val request = requestBuilder.build()
                chain.proceed(request)
            }
            .readTimeout(30,TimeUnit.SECONDS)
            .connectTimeout(30,TimeUnit.SECONDS)
            .retryOnConnectionFailure(true)
            .build()

    val apiService: ApiService
       = Retrofit.Builder()
            .baseUrl("https://mobile-test.mysapogo.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
        .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
            .build()
            .create(ApiService::class.java)
}