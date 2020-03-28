package com.exemplo.astroimagemdodia.data.services

import com.exemplo.astroimagemdodia.data.calladapter.Observable
import com.exemplo.astroimagemdodia.data.entities.ImageDayDataEntity
import retrofit2.http.GET

interface NasaService {
    @GET("planetary/apod")
    fun getImageDay(): Observable<ImageDayDataEntity>
}