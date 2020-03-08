package com.exemplo.astroimagemdodia.data.repository

import com.exemplo.astroimagemdodia.data.model.DayImage
import com.exemplo.astroimagemdodia.remote.NasaService
import retrofit2.Call
import retrofit2.Callback

class ImageDayRepository(
    private val retrofit: retrofit2.Retrofit) {

    fun getImageDay(callback: Callback<DayImage>)
    {
        val nasaService: NasaService = retrofit.create(NasaService::class.java)
        val requestGetImageDay: Call<DayImage> = nasaService.getImageDay()
        requestGetImageDay.enqueue(callback)
    }
}