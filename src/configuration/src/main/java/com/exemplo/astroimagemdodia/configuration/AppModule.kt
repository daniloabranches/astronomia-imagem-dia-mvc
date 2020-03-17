package com.exemplo.astroimagemdodia.configuration

import com.exemplo.astroimagemdodia.data.repositories.ImageDayDataRepository
import com.exemplo.astroimagemdodia.domain.repositories.ImageDayRepository
import com.exemplo.astroimagemdodia.domain.usecases.GetImageDayUseCase
import com.exemplo.astroimagemdodia.data.configuration.RetrofitModule
import com.exemplo.astroimagemdodia.data.services.NasaService

class AppModule {
    companion object {
        private val retrofit = RetrofitModule().get()

        private val nasaService by lazy {
            retrofit.create(NasaService::class.java)
        }

        private val imageDayDataRepository: ImageDayRepository =
            ImageDayDataRepository(nasaService)

        private val getImageDayUseCase = GetImageDayUseCase(imageDayDataRepository)

        fun getImageDayUseCase() = getImageDayUseCase
    }
}