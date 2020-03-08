package com.exemplo.astroimagemdodia.data.model

import com.google.gson.annotations.SerializedName

data class DayImage(
    @SerializedName("date") val Date: String,
    @SerializedName("explanation") val Explanation: String,
    @SerializedName("hdurl") val HDUrl: String,
    @SerializedName("media_type") val MediaType: String,
    @SerializedName("title") val Title: String,
    @SerializedName("url") val URL: String)