package com.exemplo.astroimagemdodia.configuration

import com.exemplo.astroimagemdodia.image.PicassoRequestImage
import com.exemplo.astroimagemdodia.image.RequestImage

class MainModule {
    companion object {
        private val requestImage: RequestImage = PicassoRequestImage()

        fun getRequestImage() = requestImage
    }
}