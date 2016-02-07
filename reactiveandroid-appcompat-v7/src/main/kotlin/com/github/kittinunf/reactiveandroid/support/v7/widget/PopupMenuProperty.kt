package com.github.kittinunf.reactiveandroid.support.v7.widget

import android.support.v7.widget.PopupMenu
import com.github.kittinunf.reactiveandroid.MutableProperty
import com.github.kittinunf.reactiveandroid.createMainThreadMutableProperty

//================================================================================
// Properties
//================================================================================

private var _rx_gravity: MutableProperty<Int>? = null

val PopupMenu.rx_gravity: MutableProperty<Int>
    get() {
        val getter = { gravity }
        val setter: (Int) -> Unit = { gravity = it }

        if (_rx_gravity == null) {
            _rx_gravity = createMainThreadMutableProperty(getter, setter)
        } else {
            _rx_gravity!!.value = getter()
        }
        return _rx_gravity!!
    }
 
