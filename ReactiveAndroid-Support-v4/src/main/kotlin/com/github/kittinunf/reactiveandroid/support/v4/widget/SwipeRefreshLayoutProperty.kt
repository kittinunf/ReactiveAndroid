package com.github.kittinunf.reactiveandroid.support.v4.widget

import android.support.v4.widget.SwipeRefreshLayout
import com.github.kittinunf.reactiveandroid.MutableProperty
import com.github.kittinunf.reactiveandroid.createMainThreadMutableProperty

//================================================================================
// Property
//================================================================================

private var _rx_refreshing: MutableProperty<Boolean>? = null

val SwipeRefreshLayout.rx_refreshing: MutableProperty<Boolean>
    get() {
        val getter = { isRefreshing }
        val setter: (Boolean) -> Unit = { isRefreshing = it }

        if (_rx_refreshing == null) {
            _rx_refreshing = createMainThreadMutableProperty(getter, setter)
        } else {
            _rx_refreshing!!.value = getter()
        }
        return _rx_refreshing!!
    }

private var _rx_nestedScrollingEnabled: MutableProperty<Boolean>? = null

val SwipeRefreshLayout.rx_nestedScrollingEnabled: MutableProperty<Boolean>
    get() {
        val getter = { isNestedScrollingEnabled }
        val setter: (Boolean) -> Unit = { isNestedScrollingEnabled = it }

        if (_rx_nestedScrollingEnabled == null) {
            _rx_nestedScrollingEnabled = createMainThreadMutableProperty(getter, setter)
        } else {
            _rx_nestedScrollingEnabled!!.value = getter()
        }
        return _rx_nestedScrollingEnabled!!
    }