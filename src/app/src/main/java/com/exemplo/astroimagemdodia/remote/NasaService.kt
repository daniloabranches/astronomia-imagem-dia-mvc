package com.exemplo.astroimagemdodia.remote

import com.exemplo.astroimagemdodia.data.model.DayImage
import retrofit2.Call
import retrofit2.http.GET

interface NasaService {
    @GET("planetary/apod")
    fun getImageDay(): Call<DayImage>
}