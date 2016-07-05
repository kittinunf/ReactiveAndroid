package com.github.kittinunf.reactiveandroid.support.design.widget

import android.support.design.widget.FloatingActionButton
import com.github.kittinunf.reactiveandroid.Action
import com.github.kittinunf.reactiveandroid.rx.addTo
import com.github.kittinunf.reactiveandroid.rx.bindTo
import com.github.kittinunf.reactiveandroid.view.rx_click
import com.github.kittinunf.reactiveandroid.view.rx_enabled
import rx.Subscription
import rx.subscriptions.CompositeSubscription

fun FloatingActionButton.rx_applyAction(action: Action<Unit, *>): Subscription {
    val subscriptions = CompositeSubscription()
    rx_enabled.bindTo(action.enabled).addTo(subscriptions)
    rx_click().map { Unit }.bindTo(action, Action<Unit, *>::execute).addTo(subscriptions)
    return subscriptions
}

 
