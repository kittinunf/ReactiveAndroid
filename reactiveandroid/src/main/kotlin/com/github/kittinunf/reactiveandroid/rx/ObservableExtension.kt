package com.github.kittinunf.reactiveandroid.rx

import com.github.kittinunf.reactiveandroid.MutablePropertyType
import rx.Observable
import rx.Observer
import rx.Subscription

fun <T : R, O, R, X> Observable<T>.bindTo(o: O,
                                          onNext: O.(R) -> X,
                                          onError: (O.(Throwable?) -> Unit)? = null,
                                          onCompleted: (O.() -> Unit)? = null): Subscription {

    return subscribe(object : Observer<T> {
        override fun onNext(t: T) {
            o.onNext(t)
        }

        override fun onError(e: Throwable?) {
            onError?.let { o.onError(e) }
        }

        override fun onCompleted() {
            onCompleted?.let { o.onCompleted() }
        }
    })
}

fun <T, O, X> Observable<T>.bindTo(o: O,
                                   onNext: O.() -> X,
                                   onError: (O.(Throwable?) -> Unit)? = null,
                                   onCompleted: (O.() -> Unit)? = null): Subscription {

    return subscribe(object : Observer<T> {
        override fun onNext(t: T) {
            o.onNext()
        }

        override fun onError(e: Throwable?) {
            onError?.let { o.onError(e) }
        }

        override fun onCompleted() {
            onCompleted?.let { o.onCompleted() }
        }
    })
}

fun <T : Pair<A, B>, A, B, O, X> Observable<T>.bindTo(o: O,
                                                      onNext: O.(A, B) -> X,
                                                      onError: (O.(Throwable?) -> Unit)? = null,
                                                      onCompleted: (O.() -> Unit)? = null): Subscription {
    return subscribe(object : Observer<T> {
        override fun onNext(t: T) {
            val (first, second) = t
            o.onNext(first, second)
        }

        override fun onError(e: Throwable?) {
            onError?.let { o.onError(e) }
        }

        override fun onCompleted() {
            onCompleted?.let { o.onCompleted() }
        }
    })
}

fun <T : Triple<A, B, C>, A, B, C, O, X> Observable<T>.bindTo(o: O,
                                                              onNext: O.(A, B, C) -> X,
                                                              onError: (O.(Throwable?) -> Unit)? = null,
                                                              onCompleted: (O.() -> Unit)? = null): Subscription {
    return subscribe(object : Observer<T> {
        override fun onNext(t: T) {
            val (first, second, third) = t
            o.onNext(first, second, third)
        }

        override fun onError(e: Throwable?) {
            onError?.let { o.onError(e) }
        }

        override fun onCompleted() {
            onCompleted?.let { o.onCompleted() }
        }
    })
}

fun <T> Observable<T>.bindTo(property: MutablePropertyType<T>): Subscription {
    return subscribe {
        property.value = it
    }
}

fun <T : Pair<A, B>, A, B> Observable<T>.subscribe(onNext: (A, B) -> Unit,
                                                   onError: ((Throwable?) -> Unit)? = null,
                                                   onCompleted: (() -> Unit)? = null): Subscription {
    return subscribe(object : Observer<T> {
        override fun onNext(t: T) {
            val (first, second) = t
            onNext(first, second)
        }

        override fun onError(e: Throwable?) {
            onError?.let { it(e) }
        }

        override fun onCompleted() {
            onCompleted?.let { it() }
        }
    })
}

fun <T : Triple<A, B, C>, A, B, C> Observable<T>.subscribe(onNext: (A, B, C) -> Unit,
                                                           onError: ((Throwable?) -> Unit)? = null,
                                                           onCompleted: (() -> Unit)? = null): Subscription {
    return subscribe(object : Observer<T> {
        override fun onNext(t: T) {
            val (first, second, third) = t
            onNext(first, second, third)
        }

        override fun onError(e: Throwable?) {
            onError?.let { it(e) }
        }

        override fun onCompleted() {
            onCompleted?.let { it() }
        }
    })
}
