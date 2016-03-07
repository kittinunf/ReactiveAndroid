package com.github.kittinunf.reactiveandroid.support.v4.widget

import android.support.v4.widget.SwipeRefreshLayout
import com.github.kittinunf.reactiveandroid.Action
import com.github.kittinunf.reactiveandroid.rx.addTo
import com.github.kittinunf.reactiveandroid.rx.bindTo
import rx.Subscription
import rx.subscriptions.CompositeSubscription

fun SwipeRefreshLayout.rx_applyAction(action: Action<Unit, *>): Subscription {
    val subscriptions = CompositeSubscription()
    rx_refreshing.bindTo(action.enabled).addTo(subscriptions)
    rx_refresh().map { Unit }.bindTo(action, Action<Unit, *>::execute).addTo(subscriptions)
    return subscriptions
}

 
