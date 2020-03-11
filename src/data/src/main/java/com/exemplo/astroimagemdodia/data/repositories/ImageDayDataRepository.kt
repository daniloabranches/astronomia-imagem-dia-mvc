package com.exemplo.astroimagemdodia.data.repositories

import com.exemplo.astroimagemdodia.data.entities.ImageDayData
import com.exemplo.astroimagemdodia.data.remote.NasaService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import java.util.*

class ImageDayDataRepository(private val retrofit: Retrofit) : com.exemplo.astroimagemdodia.domain.repositories.ImageDayRepository {
    override fun getImageDay(): Observable {
        return GetImageDayObservable(retrofit)
    }

    inner class GetImageDayObservable(private val retrofit: Retrofit) : Observable() {
        fun execute(){
            val nasaService: NasaService = retrofit.create(NasaService::class.java)

            val requestGetImageDay: Call<ImageDayData> = nasaService.getImageDay()

            requestGetImageDay.enqueue(object : Callback<ImageDayData> {
                override fun onResponse(call: Call<ImageDayData>, response: Response<ImageDayData>) {
                    this@GetImageDayObservable.setChanged()
                    this@GetImageDayObservable.notifyObservers(response.body())
                }

                override fun onFailure(call: Call<ImageDayData>, t: Throwable) {
                    this@GetImageDayObservable.setChanged()
                    this@GetImageDayObservable.notifyObservers()
                }
            })
        }
    }
}