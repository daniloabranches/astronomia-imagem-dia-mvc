package com.exemplo.astroimagemdodia.ui.main

import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.exemplo.astroimagemdodia.R
import com.exemplo.astroimagemdodia.configuration.AppModule
import com.exemplo.astroimagemdodia.configuration.MainModule
import com.exemplo.astroimagemdodia.domain.entities.ImageDayEntity
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
        shimmer_view_container.visibility = View.VISIBLE
        main_view_container.visibility = View.GONE
        shimmer_view_container.startShimmer()

        val isOnline = checkIsOnline()
        if (!isOnline){
            hideShimmerView()
            showNetworkErrorMessage()
            return
        }

        getImageDayUseCase.execute(Observer { _, imageDay ->
            hideShimmerView()

            if (imageDay is ImageDayEntity) {
                this@MainActivity.setupMain(imageDay)
            } else {
                /*setupMain(ImageDayEntity(
                    "30/03/2020",
                    "What creates Saturn's colors? The featured picture of Saturn only slightly exaggerates what a human would see if hovering close to the giant ringed world. The image was taken in 2005 by the robot Cassini spacecraft that orbited Saturn from 2004 to 2017. Here Saturn's majestic rings appear directly only as a curved line, appearing brown, in part, from its infrared glow. The rings best show their complex structure in the dark shadows they create across the upper part of the planet. The northern hemisphere of Saturn can appear partly blue for the same reason that Earth's skies can appear blue -- molecules in the cloudless portions of both planet's atmospheres are better at scattering blue light than red. When looking deep into Saturn's clouds, however, the natural gold hue of Saturn's clouds becomes dominant. It is not known why southern Saturn does not show the same blue hue -- one hypothesis holds that clouds are higher there. It is also not known why some of Saturn's clouds are colored gold.",
                    "https://apod.nasa.gov/apod/image/2003/SaturnColors_CassiniSchmidt_960.jpg",
                    "image",
                    "The Colors of Saturn from Cassini",
                    "https://apod.nasa.gov/apod/image/2003/SaturnColors_CassiniSchmidt_960.jpg"
                ))*/

                this@MainActivity.showMessageError()
            }
        })
    }

    private fun checkIsOnline(): Boolean {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val connectivityManager = getSystemService(CONNECTIVITY_SERVICE) as ConnectivityManager
            val activeNetwork = connectivityManager.activeNetwork

            if (activeNetwork != null){
                val capabilities =
                    connectivityManager.getNetworkCapabilities(activeNetwork)

                capabilities?.let {
                    return capabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
                }
            }
            else {
                return false
            }
        }
        else {
            TODO("Not yet implemented")
        }

        return true
    }

    private fun hideShimmerView(){
        shimmer_view_container.stopShimmer()
        shimmer_view_container.visibility = View.GONE
    }

    private fun setupMain(dayImage: ImageDayEntity) {
        main_view_container.visibility = View.VISIBLE

        title_view.text = dayImage.Title
        date_view.text = dayImage.Date
        explanation_view.text = dayImage.Explanation

        requestImage.load(dayImage.URL, url_image_view)
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