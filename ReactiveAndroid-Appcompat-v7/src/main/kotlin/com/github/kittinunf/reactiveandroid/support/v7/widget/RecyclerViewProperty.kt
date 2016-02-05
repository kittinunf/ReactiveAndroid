package com.github.kittinunf.reactiveandroid.support.v7.widget

import android.support.v7.widget.RecyclerView
import com.github.kittinunf.reactiveandroid.MutableProperty
import com.github.kittinunf.reactiveandroid.createMainThreadMutableProperty

//================================================================================
// Properties
//================================================================================

private var _rx_adapter: MutableProperty<RecyclerView.Adapter<*>>? = null

val RecyclerView.rx_adapter: MutableProperty<RecyclerView.Adapter<*>>
    get() {
        val getter = { adapter }
        val setter: (RecyclerView.Adapter<*>) -> Unit = { adapter = it }

        if (_rx_adapter == null) {
            _rx_adapter = createMainThreadMutableProperty(getter, setter)
        } else {
            _rx_adapter!!.value = getter()
        }
        return _rx_adapter!!
    }

private var _rx_hasFixedSize: MutableProperty<Boolean>? = null

val RecyclerView.rx_hasFixedSize: MutableProperty<Boolean>
    get() {
        val getter = { hasFixedSize() }
        val setter: (Boolean) -> Unit = { setHasFixedSize(it) }

        if (_rx_hasFixedSize == null) {
            _rx_hasFixedSize = createMainThreadMutableProperty(getter, setter)
        } else {
            _rx_hasFixedSize!!.value = getter()
        }
        return _rx_hasFixedSize!!
    }

private var _rx_itemAnimator: MutableProperty<RecyclerView.ItemAnimator>? = null

val RecyclerView.rx_itemAnimator: MutableProperty<RecyclerView.ItemAnimator>
    get() {
        val getter = { itemAnimator }
        val setter: (RecyclerView.ItemAnimator) -> Unit = { itemAnimator = it }

        if (_rx_itemAnimator == null) {
            _rx_itemAnimator = createMainThreadMutableProperty(getter, setter)
        } else {
            _rx_itemAnimator!!.value = getter()
        }
        return _rx_itemAnimator!!
    }

private var _rx_layoutManager: MutableProperty<RecyclerView.LayoutManager>? = null

val RecyclerView.rx_layoutManager: MutableProperty<RecyclerView.LayoutManager>
    get() {
        val getter = { layoutManager }
        val setter: (RecyclerView.LayoutManager) -> Unit = { layoutManager = it }

        if (_rx_layoutManager == null) {
            _rx_layoutManager = createMainThreadMutableProperty(getter, setter)
        } else {
            _rx_layoutManager!!.value = getter()
        }
        return _rx_layoutManager!!
    }

private var _rx_nestedScrollingEnabled: MutableProperty<Boolean>? = null

val RecyclerView.rx_nestedScrollingEnabled: MutableProperty<Boolean>
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

