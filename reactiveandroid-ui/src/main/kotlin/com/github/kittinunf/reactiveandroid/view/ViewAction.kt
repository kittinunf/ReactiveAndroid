package com.github.kittinunf.reactiveandroid.view

import android.view.View
import com.github.kittinunf.reactiveandroid.Action
import com.github.kittinunf.reactiveandroid.MutableProperty
import com.github.kittinunf.reactiveandroid.rx.addTo
import com.github.kittinunf.reactiveandroid.rx.bindTo
import rx.Subscription
import rx.subscriptions.CompositeSubscription

fun <R> View.rx_applyAction(action: Action<Unit, *>, viewPropertyEnabled: MutableProperty<R>, map: (Boolean) -> R): Subscription {
    val subscriptions = CompositeSubscription()
    action.enabled.map(map).bindTo(viewPropertyEnabled).addTo(subscriptions)
    rx_click().map { Unit }.bindTo(action, Action<Unit, *>::execute).addTo(subscriptions)
    return subscriptions
}


 
