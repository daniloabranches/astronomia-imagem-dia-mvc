package com.exemplo.astroimagemdodia.data.configuration

import com.exemplo.astroimagemdodia.data.calladapter.CallAdapterFactory
import com.exemplo.astroimagemdodia.data.interceptors.ApiKeyInterceptor
import com.exemplo.astroimagemdodia.data.services.ConfigService
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitModule {

    fun get(): Retrofit {
        val configService =
            ConfigService()

        val okHttpClient = OkHttpClient.Builder()
            .addInterceptor(ApiKeyInterceptor(configService.getApiKey())).build()

        return Retrofit.Builder()
            .baseUrl(configService.getApiUrl())
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(CallAdapterFactory.create())
            .build()
    }
}