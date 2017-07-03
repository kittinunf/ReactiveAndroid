package com.github.kittinunf.reactiveandroid.helper

import io.reactivex.Observable
import io.reactivex.functions.BiFunction
import io.reactivex.functions.Function3

object Observables {

    inline fun <T1, T2, R> combineLatest(s1: Observable<T1>, s2: Observable<T2>, crossinline f: (T1, T2) -> R) =
            Observable.combineLatest<T1, T2, R>(s1, s2, BiFunction { t1, t2 -> f(t1, t2) })

    inline fun <T1, T2, T3, R> combineLatest(s1: Observable<T1>, s2: Observable<T2>, s3: Observable<T3>, crossinline f: (T1, T2, T3) -> R) =
            Observable.combineLatest<T1, T2, T3, R>(s1, s2, s3, Function3 { t1, t2, t3 -> f(t1, t2, t3) })
}
