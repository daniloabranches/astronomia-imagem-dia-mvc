package com.exemplo.astroimagemdodia.data.calladapter

import retrofit2.CallAdapter
import retrofit2.Retrofit
import java.lang.reflect.ParameterizedType
import java.lang.reflect.Type

class CallAdapterFactory private constructor() : CallAdapter.Factory() {
    override fun get(
        returnType: Type,
        annotations: Array<Annotation>,
        retrofit: Retrofit
    ): CallAdapter<*, *>? {
        return try {
            val parameterizedType = (returnType as ParameterizedType)
            return if (parameterizedType.rawType == Observable::class.java) {
                val type = parameterizedType.actualTypeArguments[0]
                CallAdapter<Any>(type)
            }
            else null
        } catch (ex: ClassCastException) {
            null
        }
    }

    companion object {
        fun create() = CallAdapterFactory()
    }
}