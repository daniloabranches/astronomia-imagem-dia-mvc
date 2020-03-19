package com.exemplo.astroimagemdodia.data.calladapter

import retrofit2.Call
import java.lang.reflect.Type

class CallAdapter<R>(private val responseType: Type): retrofit2.CallAdapter<R, Any> {
    override fun responseType(): Type = responseType
    override fun adapt(call: Call<R>): Any  = Observable(call)
}