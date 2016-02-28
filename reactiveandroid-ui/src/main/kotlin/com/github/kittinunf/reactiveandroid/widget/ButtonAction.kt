package com.github.kittinunf.reactiveandroid.widget

import android.widget.Button
import com.github.kittinunf.reactiveandroid.Action
import com.github.kittinunf.reactiveandroid.rx.addTo
import com.github.kittinunf.reactiveandroid.view.rx_click
import com.github.kittinunf.reactiveandroid.view.rx_enabled
import rx.subscriptions.CompositeSubscription

private var _rx_action: Action<Unit, *>? = null

private var _rx_subscriptions: CompositeSubscription? = null

var Button.rx_action: Action<Unit, *>?
    set(value) {
        _rx_action = value

        //disposable existing
        _rx_subscriptions?.unsubscribe()

        _rx_action?.let { action ->
            //create new one after unsubscribe as CompositeSubscription is useless after unsubscribe
            _rx_subscriptions = CompositeSubscription()

            rx_enabled.bindTo(action.enabled).addTo(_rx_subscriptions!!)

            rx_click().subscribe { view ->
                action.execute(Unit)
            }.addTo(_rx_subscriptions!!)
        }
    }
    get() {
        return _rx_action
    }
 
