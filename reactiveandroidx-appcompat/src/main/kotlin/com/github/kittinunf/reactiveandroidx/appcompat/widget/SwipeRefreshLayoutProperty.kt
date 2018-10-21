package com.github.kittinunf.reactiveandroidx.appcompat.widget

import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.github.kittinunf.reactiveandroid.MutableProperty
import com.github.kittinunf.reactiveandroid.createMainThreadMutableProperty

//================================================================================
// Property
//================================================================================

val SwipeRefreshLayout.rx_refreshing: MutableProperty<Boolean>
    get() {
        val getter = { isRefreshing }
        val setter: (Boolean) -> Unit = { isRefreshing = it }

        return createMainThreadMutableProperty(getter, setter)
    }

val SwipeRefreshLayout.rx_nestedScrollingEnabled: MutableProperty<Boolean>
    get() {
        val getter = { isNestedScrollingEnabled }
        val setter: (Boolean) -> Unit = { isNestedScrollingEnabled = it }

        return createMainThreadMutableProperty(getter, setter)
    }
