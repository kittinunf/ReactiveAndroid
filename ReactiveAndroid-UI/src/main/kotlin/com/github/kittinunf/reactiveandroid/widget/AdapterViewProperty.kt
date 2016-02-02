package com.github.kittinunf.reactiveandroid.widget

import android.view.View
import android.widget.AdapterView
import com.github.kittinunf.reactiveandroid.MutableProperty
import com.github.kittinunf.reactiveandroid.createMainThreadMutableProperty

//================================================================================
// Properties
//================================================================================

private var _rx_emptyView: MutableProperty<View>? = null

val AdapterView<*>.rx_emptyView: MutableProperty<View>
    get() {
        val getter = { emptyView }
        val setter: (View) -> Unit = { emptyView = it }

        if (_rx_emptyView == null) {
            _rx_emptyView = createMainThreadMutableProperty(getter, setter)
        } else {
            _rx_emptyView!!.value = getter()
        }
        return _rx_emptyView!!
    }

private var _rx_selection: MutableProperty<Int>? = null

val AdapterView<*>.rx_selection: MutableProperty<Int>
    get() {
        val getter = { selectedItemPosition }
        val setter: (Int) -> Unit = { setSelection(it) }

        if (_rx_selection == null) {
            _rx_selection = createMainThreadMutableProperty(getter, setter)
        } else {
            _rx_selection!!.value = getter()
        }
        return _rx_selection!!
    }

