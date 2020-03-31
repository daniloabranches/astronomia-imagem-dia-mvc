package com.exemplo.astroimagemdodia.image

import android.widget.ImageView

interface RequestImage {
    fun load(url: String, view: ImageView, callback: Callback)
}