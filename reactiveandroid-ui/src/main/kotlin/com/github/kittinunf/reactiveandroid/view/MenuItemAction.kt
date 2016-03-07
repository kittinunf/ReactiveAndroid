package com.github.kittinunf.reactiveandroid.view

import android.view.MenuItem
import com.github.kittinunf.reactiveandroid.Action
import com.github.kittinunf.reactiveandroid.rx.addTo
import com.github.kittinunf.reactiveandroid.rx.bindTo
import rx.Subscription
import rx.subscriptions.CompositeSubscription

fun MenuItem.rx_applyAction(action: Action<Unit, *>): Subscription {
    val subscriptions = CompositeSubscription()
    rx_enabled.bindTo(action.enabled).addTo(subscriptions)
    rx_menuItemClick(true).map { Unit }.bindTo(action, Action<Unit, *>::execute).addTo(subscriptions)
    return subscriptions
}

