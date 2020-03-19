package com.exemplo.astroimagemdodia.data.repositories

import com.exemplo.astroimagemdodia.data.calladapter.Mapper
import com.exemplo.astroimagemdodia.data.entities.ImageDayData
import com.exemplo.astroimagemdodia.data.services.NasaService
import com.exemplo.astroimagemdodia.domain.entities.ImageDayEntity
import java.util.*

class ImageDayDataRepository(
    private val nasaService: NasaService
) : com.exemplo.astroimagemdodia.domain.repositories.ImageDayRepository {

    private val mapper = object : Mapper<ImageDayData> {
        override fun execute(data: ImageDayData?): Any? {
            return data?.let {
                ImageDayEntity(
                    it.Date,
                    it.Explanation,
                    it.HDUrl,
                    it.MediaType,
                    it.Title,
                    it.URL
                )
            }
        }
    }

    override fun getImageDay(observer: Observer): Observable {
        return nasaService.getImageDay()
            .subscribe(observer)
            .map(mapper)
            .execute()
    }
}