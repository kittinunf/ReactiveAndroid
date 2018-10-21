package com.github.kittinunf.reactiveandroid.scheduler

import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

object AndroidThreadScheduler {

    var main: Scheduler by readWriteLazy { AndroidSchedulers.mainThread() }

}

private fun <T> readWriteLazy(initializer: () -> T): ReadWriteProperty<Any?, T> = ReadWriteLazyVal(initializer)

private class ReadWriteLazyVal<T>(private val initializer: () -> T) : ReadWriteProperty<Any?, T> {

    private var value: Any? = null

    override operator fun getValue(thisRef: Any?, property: KProperty<*>): T {
        if (value == null) {
            value = (initializer()) ?: throw IllegalStateException("Initializer block of property ${property.name} return null")
        }
        @Suppress("UNCHECKED_CAST")
        return value as T
    }

    override operator fun setValue(thisRef: Any?, property: KProperty<*>, value: T) {
        this.value = value
    }

}
