package com.exemplo.astroimagemdodia.common

class Observable<T> : java.util.Observable() {
    fun setData(data: T?){
        setChanged()
        notifyObservers(data)
    }
}