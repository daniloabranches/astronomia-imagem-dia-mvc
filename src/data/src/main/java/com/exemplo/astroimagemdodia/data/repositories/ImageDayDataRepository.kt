package com.exemplo.astroimagemdodia.data.repositories

import com.exemplo.astroimagemdodia.common.observer.Observable
import com.exemplo.astroimagemdodia.data.entities.ImageDayData
import com.exemplo.astroimagemdodia.data.services.NasaService
import com.exemplo.astroimagemdodia.domain.entities.ImageDayEntity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ImageDayDataRepository(
    private val nasaService: NasaService
) : com.exemplo.astroimagemdodia.domain.repositories.ImageDayRepository {

    private val getImageDayObservable by lazy {
        Observable()
    }

    override fun getImageDay(observer: java.util.Observer): Observable {
        getImageDayObservable.addObserver(observer)

        nasaService.getImageDay().enqueue(object : Callback<ImageDayData> {
            override fun onResponse(call: Call<ImageDayData>, response: Response<ImageDayData>) {

                response.body()?.let {
                    getImageDayObservable.setData(
                        ImageDayEntity(it.Date, it.Explanation,
                            it.HDUrl, it.MediaType, it.Title, it.URL)
                    )
                    return
                }

                getImageDayObservable.setData(null)
            }

            override fun onFailure(call: Call<ImageDayData>, t: Throwable) {
                getImageDayObservable.setData(null)
            }
        })

        return getImageDayObservable
    }
}