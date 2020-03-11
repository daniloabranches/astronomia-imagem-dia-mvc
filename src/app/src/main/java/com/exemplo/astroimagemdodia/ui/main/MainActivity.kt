package com.exemplo.astroimagemdodia.ui.main

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.exemplo.astroimagemdodia.R
import com.exemplo.astroimagemdodia.data.entities.ImageDayData
import com.exemplo.astroimagemdodia.data.remote.retrofit.RetrofitModule
import com.exemplo.astroimagemdodia.data.repositories.ImageDayDataRepository
import com.exemplo.astroimagemdodia.domain.usecases.GetImageDayUseCase
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_main.*

class MainActivity : AppCompatActivity() {

    private val retrofit = RetrofitModule().get()
    private val imageDayDataRepository = ImageDayDataRepository(retrofit)
    private val getImageDayUseCase = GetImageDayUseCase(imageDayDataRepository)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        this.getDataImageDay()
    }

    private fun getDataImageDay(){
        val getImageDayObservable = getImageDayUseCase.execute() as ImageDayDataRepository.GetImageDayObservable

        getImageDayObservable.addObserver { _, arg ->
            if (arg is ImageDayData) {
                this@MainActivity.setupMain(arg)
            } else {
                this@MainActivity.showMessageError()
            }
        }

        getImageDayObservable.execute()
    }

    private fun setupMain(dayImage: ImageDayData){
        txtTitle.text = dayImage.Title
        txtDate.text = dayImage.Date
        txtExplanation.text = dayImage.Explanation

        Picasso.get().load(dayImage.URL).into(imgUrl)
    }

    private fun showMessageError(){
        Toast.makeText(this@MainActivity, R.string.message_error_load_data_image_day, Toast.LENGTH_SHORT).show()
    }
}