package com.exemplo.astroimagemdodia.common.observer

class Observable : java.util.Observable() {
    fun setData(data: Any?){
        setChanged()
        notifyObservers(data)
    }
}