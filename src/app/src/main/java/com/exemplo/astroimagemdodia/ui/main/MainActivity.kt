package com.exemplo.astroimagemdodia.ui.main

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.exemplo.astroimagemdodia.R
import com.exemplo.astroimagemdodia.data.model.DayImage
import com.exemplo.astroimagemdodia.remote.retrofit.RetrofitModule
import com.exemplo.astroimagemdodia.data.repository.ImageDayRepository
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_main.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        this.getDataImageDay()
    }

    private fun getDataImageDay(){
        val retrofit = RetrofitModule().get()
        ImageDayRepository(retrofit)
            .getImageDay(object : Callback<DayImage> {
            override fun onResponse(call: Call<DayImage>, response: Response<DayImage>) {
                response.body()?.let {
                    setupMain(it)
                    return
                }

                this@MainActivity.showMessageError()
            }

            override fun onFailure(call: Call<DayImage>, t: Throwable) {
                this@MainActivity.showMessageError()
            }
        })
    }

    private fun setupMain(dayImage: DayImage){
        txtTitle.text = dayImage.Title
        txtDate.text = dayImage.Date
        txtExplanation.text = dayImage.Explanation

        Picasso.get().load(dayImage.URL).into(imgUrl)
    }

    private fun showMessageError(){
        Toast.makeText(this@MainActivity, R.string.message_error_load_data_image_day, Toast.LENGTH_SHORT).show()
    }
}