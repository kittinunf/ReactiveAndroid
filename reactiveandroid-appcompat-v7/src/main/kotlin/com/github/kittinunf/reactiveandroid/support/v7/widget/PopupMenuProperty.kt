package com.github.kittinunf.reactiveandroid.support.v7.widget

import android.support.v7.widget.PopupMenu
import com.github.kittinunf.reactiveandroid.MutableProperty
import com.github.kittinunf.reactiveandroid.createMainThreadMutableProperty

//================================================================================
// Properties
//================================================================================

val PopupMenu.rx_gravity: MutableProperty<Int>
    get() {
        val getter = { gravity }
        val setter: (Int) -> Unit = { gravity = it }

        return createMainThreadMutableProperty(getter, setter)
    }
