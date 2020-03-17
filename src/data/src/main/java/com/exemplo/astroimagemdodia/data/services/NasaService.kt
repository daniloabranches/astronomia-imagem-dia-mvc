package com.exemplo.astroimagemdodia.data.services

import com.exemplo.astroimagemdodia.data.entities.ImageDayData
import retrofit2.Call
import retrofit2.http.GET

interface NasaService {
    @GET("planetary/apod")
    fun getImageDay(): Call<ImageDayData>
}