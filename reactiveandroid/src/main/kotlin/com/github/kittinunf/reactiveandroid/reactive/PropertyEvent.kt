package com.github.kittinunf.reactiveandroid.reactive

import io.reactivex.Observable
import io.reactivex.Observer
import io.reactivex.functions.Consumer

class PropertyEvent<T>(val startingValue: () -> T, val emitter: () -> T, val disposer: () -> Unit) : Observable<T>() {

    private val cache = Cache<T>()

    override fun subscribeActual(observer: Observer<in T>) {
        val firstValue = startingValue()
        observer.onNext(firstValue)
    }

    private class Cache<T> : Consumer<T> {
        var value: T? = null
        override fun accept(t: T) {
            value = t
        }
    }

}

