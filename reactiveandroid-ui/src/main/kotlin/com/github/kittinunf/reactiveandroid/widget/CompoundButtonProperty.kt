package com.github.kittinunf.reactiveandroid.widget

import android.graphics.drawable.Drawable
import android.widget.CompoundButton
import com.github.kittinunf.reactiveandroid.MutableProperty
import com.github.kittinunf.reactiveandroid.createMainThreadMutableProperty

//================================================================================
// Properties 
//================================================================================

val CompoundButton.rx_buttonDrawable: MutableProperty<Drawable>
    get() {
        val getter = { buttonDrawable }
        val setter: (Drawable) -> Unit = { buttonDrawable = it }

        return createMainThreadMutableProperty(getter, setter)
    }

val CompoundButton.rx_checked: MutableProperty<Boolean>
    get() {
        val getter = { isChecked }
        val setter: (Boolean) -> Unit = { isChecked = it }

        return createMainThreadMutableProperty(getter, setter)
    }

