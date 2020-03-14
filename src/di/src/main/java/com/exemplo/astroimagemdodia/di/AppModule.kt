package com.exemplo.astroimagemdodia.di

import com.exemplo.astroimagemdodia.data.remote.retrofit.RetrofitModule
import com.exemplo.astroimagemdodia.data.repositories.ImageDayDataRepository
import com.exemplo.astroimagemdodia.domain.repositories.ImageDayRepository

class AppModule {
    companion object {
        private val retrofit = RetrofitModule().get()
        private val imageDayDataRepository: ImageDayRepository = ImageDayDataRepository(retrofit)

        fun getImageDayDataRepository() = imageDayDataRepository
    }
}