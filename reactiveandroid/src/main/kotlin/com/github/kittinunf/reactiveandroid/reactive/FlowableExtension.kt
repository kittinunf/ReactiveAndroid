package com.github.kittinunf.reactiveandroid.reactive

import com.github.kittinunf.reactiveandroid.MutablePropertyType
import io.reactivex.Flowable
import io.reactivex.disposables.Disposable

fun <T : R, R> Flowable<T>.bindTo(property: MutablePropertyType<R>): Disposable {
    return subscribe {
        property.value = it
    }
}

fun <T> Flowable<T>.cachedPrevious(): Flowable<Pair<T?, T?>> {
    return scan(Pair<T?, T?>(null, null)) { (_, j), item -> j to item }
            .skip(1)
}

inline fun <reified T> Flowable<in T>.ofType() = ofType(T::class.java)