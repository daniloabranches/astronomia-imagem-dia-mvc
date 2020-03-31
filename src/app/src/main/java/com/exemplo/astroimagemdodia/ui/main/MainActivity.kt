package com.exemplo.astroimagemdodia.ui.main

import android.os.Bundle
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
            getImageDayUseCase.execute(Observer { _, imageDay ->
                if (imageDay is ImageDayEntity) {
                    this@MainActivity.downloadImageFile(imageDay)
                } else {
                    hideShimmerView()
                    this@MainActivity.showMessageError()
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

    private fun downloadImageFile(imageDay: ImageDayEntity) {
        requestImage.load(imageDay.URL, url_image_view, object : Callback {
            override fun onSuccess() = setupMain(imageDay)

            override fun onError(e: Exception?) {
                hideShimmerView()
                showMessageError()
            }
        })
    }

    private fun setupMain(imageDay: ImageDayEntity) {
        hideShimmerView()

        main_view_container.visibility = View.VISIBLE

        title_view.text = imageDay.Title
        date_view.text = imageDay.Date
        explanation_view.text = imageDay.Explanation
    }

    private fun showMessageError() {
        Toast.makeText(
            this@MainActivity,
            R.string.generic_error_message,
            Toast.LENGTH_SHORT
        ).show()
    }

    private fun showNetworkErrorMessage() {
        Toast.makeText(
            this@MainActivity,
            getString(R.string.network_error_message),
            Toast.LENGTH_SHORT
        ).show()
    }

    override fun onPause() {
        super.onPause()
        shimmer_view_container.stopShimmer()
    }
}