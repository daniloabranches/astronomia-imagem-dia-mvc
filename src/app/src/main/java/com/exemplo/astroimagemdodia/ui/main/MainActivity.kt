package com.exemplo.astroimagemdodia.ui.main

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.exemplo.astroimagemdodia.R
import com.exemplo.astroimagemdodia.compat.ConnectivityManagerCompat
import com.exemplo.astroimagemdodia.configuration.AppModule
import com.exemplo.astroimagemdodia.configuration.MainModule
import com.exemplo.astroimagemdodia.domain.entities.ImageDayEntity
import com.exemplo.astroimagemdodia.image.Callback
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_main.*
import kotlinx.android.synthetic.main.shimmer_placeholder_layout.*
import java.util.*

class MainActivity : AppCompatActivity() {
    private val getImageDayUseCase = AppModule.getImageDayUseCase()
    private val requestImage = MainModule.getRequestImage()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        this.initializeActivity()
    }

    private fun initializeActivity() {
        main_view_container.visibility = View.GONE
        shimmer_view_container.visibility = View.VISIBLE
        shimmer_view_container.startShimmer()

        val isOnline = ConnectivityManagerCompat.isConnected(this)
        if (isOnline) {
            getImageDayUseCase.execute(Observer { _, data ->
                if (data is ImageDayEntity) {
                    setupMain(data)
                } else {
                    if (data is Throwable) {
                        //log api
                        Log.d("USE_CASE", "Error getImageDayUseCase", data)
                    }

                    hideShimmerView()
                    showMessageError()
                }
            })
        } else {
            hideShimmerView()
            showNetworkErrorMessage()
        }
    }

    private fun hideShimmerView() {
        shimmer_view_container.stopShimmer()
        shimmer_view_container.visibility = View.GONE
    }

    private fun setupMain(imageDay: ImageDayEntity) {
        when (imageDay.MediaType) {
            "image" -> setupMainWithImage(imageDay)
            else -> setupMainWithoutImage(imageDay)
        }
    }

    private fun setupMainWithImage(imageDay: ImageDayEntity) {
        requestImage.load(imageDay.URL, url_image_view, object : Callback {
            override fun onSuccess() = showContentMain(imageDay)

            override fun onError(e: Exception?) {
                //log api
                Log.d("SETUP_MAIN", "Error setupMain", e)

                hideShimmerView()
                showMessageError()
            }
        })
    }

    private fun setupMainWithoutImage(imageDay: ImageDayEntity) {
        showImageNotFoundErrorMessage()

        url_image_view.visibility = View.GONE
        date_view.visibility = View.GONE
        showContentMain(imageDay)
    }

    private fun showContentMain(imageDay: ImageDayEntity) {
        hideShimmerView()

        main_view_container.visibility = View.VISIBLE

        title_view.text = imageDay.Title
        date_view.text = formatDate(imageDay.Date)
        explanation_view.text = imageDay.Explanation
    }

    private fun formatDate(date: String): String? {
        val (year, month, day) = date.split('-')
        return String.format("%s/%s/%s", day, month, year)
    }

    private fun showMessageError() {
        Toast.makeText(
            this,
            R.string.generic_error_message,
            Toast.LENGTH_SHORT
        ).show()
    }

    private fun showNetworkErrorMessage() {
        Toast.makeText(
            this,
            getString(R.string.network_error_message),
            Toast.LENGTH_SHORT
        ).show()
    }

    private fun showImageNotFoundErrorMessage() {
        Toast.makeText(
            this,
            getString(R.string.not_found_image_message_error),
            Toast.LENGTH_SHORT
        ).show()
    }

    override fun onPause() {
        super.onPause()
        shimmer_view_container.stopShimmer()
    }
}