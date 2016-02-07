package com.github.kittinunf.reactiveandroid.widget

import android.view.View
import android.widget.Button
import com.github.kittinunf.reactiveandroid.Action
import com.github.kittinunf.reactiveandroid.view.rx_click
import com.github.kittinunf.reactiveandroid.view.rx_enabled
import rx.subscriptions.CompositeSubscription

private var _rx_action: Action<View, *>? = null

private var _rx_actionSubscription = CompositeSubscription()

var Button.rx_action: Action<View, *>?
    set(value) {
        _rx_action = value

        //disposable existing
        _rx_actionSubscription.unsubscribe()

        _rx_action?.let { action ->

            rx_enabled.bindTo(action.enabled)

            rx_click().subscribe {
                action.execute(it)
            }
        }
    }
    get() {
        return _rx_action
    }
 
