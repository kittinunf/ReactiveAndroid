package com.github.kittinunf.reactiveandroid.rx

import rx.Subscription
import rx.subscriptions.CompositeSubscription

fun Subscription.addTo(compositeSubscription: CompositeSubscription) {
    compositeSubscription += this
}
