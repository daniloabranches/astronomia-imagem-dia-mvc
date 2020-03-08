package com.exemplo.astroimagemdodia.remote.retrofit

import com.exemplo.astroimagemdodia.remote.ConfigService
import com.exemplo.astroimagemdodia.remote.retrofit.interceptor.ApiKeyInterceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitModule {

    fun get(): Retrofit {
        val configService = ConfigService()
        val okHttpClient = OkHttpClient.Builder()
            .addInterceptor(ApiKeyInterceptor(configService.getApiKey())).build()

        return Retrofit.Builder()
            .baseUrl(configService.getApiUrl())
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
}