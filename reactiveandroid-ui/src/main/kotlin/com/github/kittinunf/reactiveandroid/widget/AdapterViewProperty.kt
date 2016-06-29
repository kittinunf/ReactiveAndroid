package com.github.kittinunf.reactiveandroid.widget

import android.view.View
import android.widget.AdapterView
import com.github.kittinunf.reactiveandroid.MutableProperty
import com.github.kittinunf.reactiveandroid.createMainThreadMutableProperty

//================================================================================
// Properties
//================================================================================

val AdapterView<*>.rx_emptyView: MutableProperty<View>
    get() {
        val getter = { emptyView }
        val setter: (View) -> Unit = { emptyView = it }

        return createMainThreadMutableProperty(getter, setter)
    }

val AdapterView<*>.rx_selection: MutableProperty<Int>
    get() {
        val getter = { selectedItemPosition }
        val setter: (Int) -> Unit = { setSelection(it) }

        return createMainThreadMutableProperty(getter, setter)
    }
