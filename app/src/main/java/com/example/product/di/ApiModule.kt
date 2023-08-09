package com.example.product.di

import com.example.product.api.ApiService
import com.example.product.utils.Constant
import hu.akarnokd.rxjava3.retrofit.RxJava3CallAdapterFactory
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object ApiModule {
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
            .baseUrl(Constant.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
        .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
            .build()
            .create(ApiService::class.java)
}