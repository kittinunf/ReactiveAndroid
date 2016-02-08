package com.github.kittinunf.reactiveandroid.support.v4.widget

import android.support.v4.widget.SwipeRefreshLayout
import com.github.kittinunf.reactiveandroid.Action
import com.github.kittinunf.reactiveandroid.rx.addTo
import rx.subscriptions.CompositeSubscription

private var _rx_action: Action<Unit, *>? = null

private var _rx_subscriptions: CompositeSubscription? = null

var SwipeRefreshLayout.rx_action: Action<Unit, *>?
    set(value) {
        _rx_action = value

        //disposable existing
        _rx_subscriptions?.unsubscribe()

        _rx_action?.let { action ->
            //create new one after unsubscribe as CompositeSubscription is useless after unsubscribe
            _rx_subscriptions = CompositeSubscription()

            rx_refreshing.bindTo(action.enabled.map { !it }).addTo(_rx_subscriptions!!)

            rx_refresh().subscribe {
                action.execute(Unit)
            }.addTo(_rx_subscriptions!!)
        }
    }
    get() {
        return _rx_action
    }

 
