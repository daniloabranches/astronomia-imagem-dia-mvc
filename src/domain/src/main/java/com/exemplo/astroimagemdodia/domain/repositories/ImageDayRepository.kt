package com.exemplo.astroimagemdodia.domain.repositories

import java.util.*

interface ImageDayRepository {
    fun getImageDay(observer: Observer) : Observable
}