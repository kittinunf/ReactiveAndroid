package com.github.kittinunf.reactiveandroid.reactive

import com.github.kittinunf.reactiveandroid.MutablePropertyType
import io.reactivex.Observable
import io.reactivex.disposables.Disposable

fun <T : R, R> Observable<T>.bindTo(property: MutablePropertyType<R>): Disposable {
    return subscribe {
        property.value = it
    }
}

fun <T> Observable<T>.cachedPrevious(): Observable<Pair<T?, T>> {
    return scan(Pair<T?, T>(null, Any() as T)) { (_, j), item -> j to item }
            .skip(1)
}

inline fun <reified T> Observable<T>.ofType() = ofType(T::class.java)
