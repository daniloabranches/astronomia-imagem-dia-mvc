package com.exemplo.astroimagemdodia.image

import android.widget.ImageView
import com.squareup.picasso.Picasso

class PicassoRequestImage : RequestImage {
    override fun load(url: String, view: ImageView) {
        Picasso.get().load(url).into(view)
    }
}