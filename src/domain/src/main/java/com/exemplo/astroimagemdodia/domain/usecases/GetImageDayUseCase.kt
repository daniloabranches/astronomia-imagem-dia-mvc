package com.exemplo.astroimagemdodia.domain.usecases

import com.exemplo.astroimagemdodia.domain.repositories.ImageDayRepository
import java.util.*

class GetImageDayUseCase(private val imageDayRepository: ImageDayRepository) {
    fun execute(): Observable {
        return imageDayRepository.getImageDay()
    }
}