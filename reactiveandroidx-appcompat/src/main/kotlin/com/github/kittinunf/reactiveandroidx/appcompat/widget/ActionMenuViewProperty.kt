package com.github.kittinunf.reactiveandroidx.appcompat.widget

import android.graphics.drawable.Drawable
import androidx.appcompat.widget.ActionMenuView
import com.github.kittinunf.reactiveandroid.MutableProperty
import com.github.kittinunf.reactiveandroid.createMainThreadMutableProperty

//================================================================================
// Properties
//================================================================================

val ActionMenuView.rx_overflowIcon: MutableProperty<Drawable?>
    get() {
        val getter = { overflowIcon }
        val setter: (Drawable?) -> Unit = { overflowIcon = it }

        return createMainThreadMutableProperty(getter, setter)
    }

val ActionMenuView.rx_popupTheme: MutableProperty<Int>
    get() {
        val getter = { popupTheme }
        val setter: (Int) -> Unit = { popupTheme = it }

        return createMainThreadMutableProperty(getter, setter)
    }
