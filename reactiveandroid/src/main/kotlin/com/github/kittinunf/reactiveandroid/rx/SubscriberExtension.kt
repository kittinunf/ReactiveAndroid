package com.github.kittinunf.reactiveandroid.rx

import rx.Subscriber
import rx.subscriptions.Subscriptions

fun Subscriber<*>.add(dispose: () -> Unit) {
    add(Subscriptions.create { dispose() })
}

 
