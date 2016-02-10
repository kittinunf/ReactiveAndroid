package com.github.kittinunf.reactiveandroid.support.v7.widget

import android.support.v7.widget.RecyclerView
import com.github.kittinunf.reactiveandroid.MutableProperty
import com.github.kittinunf.reactiveandroid.createMainThreadMutableProperty

//================================================================================
// Properties
//================================================================================

val RecyclerView.rx_adapter: MutableProperty<RecyclerView.Adapter<*>>
    get() {
        val getter = { adapter }
        val setter: (RecyclerView.Adapter<*>) -> Unit = { adapter = it }

        return createMainThreadMutableProperty(getter, setter)
    }

val RecyclerView.rx_hasFixedSize: MutableProperty<Boolean>
    get() {
        val getter = { hasFixedSize() }
        val setter: (Boolean) -> Unit = { setHasFixedSize(it) }

        return createMainThreadMutableProperty(getter, setter)
    }

val RecyclerView.rx_itemAnimator: MutableProperty<RecyclerView.ItemAnimator>
    get() {
        val getter = { itemAnimator }
        val setter: (RecyclerView.ItemAnimator) -> Unit = { itemAnimator = it }

        return createMainThreadMutableProperty(getter, setter)
    }

val RecyclerView.rx_layoutManager: MutableProperty<RecyclerView.LayoutManager>
    get() {
        val getter = { layoutManager }
        val setter: (RecyclerView.LayoutManager) -> Unit = { layoutManager = it }

        return createMainThreadMutableProperty(getter, setter)
    }

val RecyclerView.rx_nestedScrollingEnabled: MutableProperty<Boolean>
    get() {
        val getter = { isNestedScrollingEnabled }
        val setter: (Boolean) -> Unit = { isNestedScrollingEnabled = it }

        return createMainThreadMutableProperty(getter, setter)
    }
