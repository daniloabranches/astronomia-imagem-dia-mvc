package com.exemplo.astroimagemdodia.data.calladapter

import android.os.Handler
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*

class Observable<R>(private val call: Call<R>) : java.util.Observable() {

    private var mapper : Mapper<R> = object : Mapper<R> {
        override fun execute(data: R?): Any? = data
    }

    fun subscribe(observer: Observer) : Observable<R> {
        addObserver(observer)
        return this
    }

    fun map(mapper: Mapper<R>) : Observable<R> {
        this.mapper = mapper
        return  this
    }

    fun execute() : Observable<R> {
        val handler = Handler()

        call.enqueue(object : Callback<R> {
            override fun onFailure(call: Call<R>, throwable: Throwable) {
                handler.post { onFailure(throwable) }
            }

            override fun onResponse(call: Call<R>, response: Response<R>) {
                handler.post { onResponse(response) }
            }
        })

        return this
    }

    private fun onFailure(throwable: Throwable) = setData(throwable)

    private fun onResponse(response: Response<R>){
        if (response.isSuccessful) {
            setData(mapper.execute(response.body()))
        } else {
            setData(Exception(String.format("Http error %d", response.code())))
        }
    }

    private fun setData(data: Any?){
        setChanged()
        notifyObservers(data)
    }
}