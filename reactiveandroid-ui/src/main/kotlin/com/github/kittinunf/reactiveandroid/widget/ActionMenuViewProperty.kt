package com.github.kittinunf.reactiveandroid.widget

import android.graphics.drawable.Drawable
import android.widget.ActionMenuView
import com.github.kittinunf.reactiveandroid.MutableProperty
import com.github.kittinunf.reactiveandroid.createMainThreadMutableProperty

//================================================================================
// Properties
//================================================================================

private var _rx_overflowIcon: MutableProperty<Drawable?>? = null

val ActionMenuView.rx_overflowIcon: MutableProperty<Drawable?>
    get() {
        val getter = { overflowIcon }
        val setter: (Drawable?) -> Unit = { overflowIcon = it }

        if (_rx_overflowIcon == null) {
            _rx_overflowIcon = createMainThreadMutableProperty(getter, setter)
        } else {
            _rx_overflowIcon!!.value = getter()
        }
        return _rx_overflowIcon!!
    }

private var _rx_popupTheme: MutableProperty<Int>? = null

val ActionMenuView.rx_popupTheme: MutableProperty<Int>
    get() {
        val getter = { popupTheme }
        val setter: (Int) -> Unit = { popupTheme = it }

        if (_rx_popupTheme == null) {
            _rx_popupTheme = createMainThreadMutableProperty(getter, setter)
        } else {
            _rx_popupTheme!!.value = getter()
        }
        return _rx_popupTheme!!
    }
