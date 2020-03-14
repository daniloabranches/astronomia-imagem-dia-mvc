package com.exemplo.astroimagemdodia.ui.main

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.exemplo.astroimagemdodia.R
import com.exemplo.astroimagemdodia.di.AppModule
import com.exemplo.astroimagemdodia.domain.entities.ImageDayEntity
import com.exemplo.astroimagemdodia.domain.usecases.GetImageDayUseCase
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_main.*
import java.util.*

class MainActivity : AppCompatActivity() {
    private val getImageDayUseCase = GetImageDayUseCase(AppModule.getImageDayDataRepository())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        this.initializeActivity()
    }

    private fun initializeActivity(){
        getImageDayUseCase.execute(Observer { _, imageDay ->
            if (imageDay is ImageDayEntity) {
                this@MainActivity.setupMain(imageDay)
            } else {
                this@MainActivity.showMessageError()
            }
         })
    }

    private fun setupMain(dayImage: ImageDayEntity){
        txtTitle.text = dayImage.Title
        txtDate.text = dayImage.Date
        txtExplanation.text = dayImage.Explanation

        Picasso.get().load(dayImage.URL).into(imgUrl)
    }

    private fun showMessageError(){
        Toast.makeText(this@MainActivity, R.string.message_error_load_data_image_day, Toast.LENGTH_SHORT).show()
    }
}