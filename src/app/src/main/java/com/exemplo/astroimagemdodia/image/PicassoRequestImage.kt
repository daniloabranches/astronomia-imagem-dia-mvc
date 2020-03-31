package com.exemplo.astroimagemdodia.image

import android.widget.ImageView
import com.squareup.picasso.Picasso

class PicassoRequestImage : RequestImage {
    override fun load(url: String, view: ImageView, callback: Callback) {
        Picasso.get().load(url).into(view, object : com.squareup.picasso.Callback {
            override fun onSuccess() = callback.onSuccess()
            override fun onError(e: Exception?) = callback.onError(e)
        })
    }
}