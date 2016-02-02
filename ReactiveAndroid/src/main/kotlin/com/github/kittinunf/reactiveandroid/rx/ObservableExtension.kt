package com.github.kittinunf.reactiveandroid.rx

import rx.Observable
import rx.Observer
import rx.Subscription

public fun <T, U> Observable<T>.lift(u: U,
                                     onNext: U.(T) -> Unit,
                                     onError: (U.(Throwable?) -> Unit)? = null,
                                     onCompleted: (U.() -> Unit)? = null): Subscription {

    return subscribe(object : Observer<T> {
        override fun onNext(t: T) {
            u.onNext(t)
        }

        override fun onError(e: Throwable?) {
            onError?.let {
                u.onError(e)
            }
        }

        override fun onCompleted() {
            onCompleted?.let {
                u.onCompleted()
            }
        }
    })
}

public fun <T : Pair<A, B>, A, B> Observable<T>.subscribe(onNext: (A, B) -> Unit,
                                                          onError: ((Throwable?) -> Unit)? = null,
                                                          onCompleted: (() -> Unit)? = null): Subscription {
    return subscribe(object : Observer<T> {
        override fun onNext(t: T) {
            val (first, second) = t
            onNext(first, second)
        }

        override fun onError(e: Throwable?) {
            onError?.let {
                it(e)
            }
        }

        override fun onCompleted() {
            onCompleted?.let {
                it()
            }
        }
    })
}

public fun <T : Triple<A, B, C>, A, B, C> Observable<T>.subscribe(onNext: (A, B, C) -> Unit,
                                                          onError: ((Throwable?) -> Unit)? = null,
                                                          onCompleted: (() -> Unit)? = null): Subscription {
    return subscribe(object : Observer<T> {
        override fun onNext(t: T) {
            val (first, second, third) = t
            onNext(first, second, third)
        }

        override fun onError(e: Throwable?) {
            onError?.let {
                it(e)
            }
        }

        override fun onCompleted() {
            onCompleted?.let {
                it()
            }
        }
    })
}
