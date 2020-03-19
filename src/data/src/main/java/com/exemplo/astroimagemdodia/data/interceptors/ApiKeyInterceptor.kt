package com.exemplo.astroimagemdodia.data.interceptors

import okhttp3.HttpUrl
import okhttp3.Interceptor

class ApiKeyInterceptor(
    private val apiKey: String) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): okhttp3.Response {
        val originalRequest = chain.request()
        val newHttpUrl = this.createNewHttpUrl(originalRequest.url())
        val newRequest = originalRequest.newBuilder().url(newHttpUrl).build()
        return chain.proceed(newRequest)
    }

    private fun createNewHttpUrl(originalHttpUrl: HttpUrl) =
        originalHttpUrl.newBuilder().addQueryParameter("api_key", apiKey).build()
}