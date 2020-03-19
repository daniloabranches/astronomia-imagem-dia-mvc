package com.exemplo.astroimagemdodia.data.calladapter

interface Mapper<T> {
    fun execute(data: T?): Any?
}