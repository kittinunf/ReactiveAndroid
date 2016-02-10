package com.github.kittinunf.reactiveandroid.widget

import android.content.res.ColorStateList
import android.graphics.PorterDuff
import android.graphics.drawable.Drawable
import android.widget.CheckedTextView
import com.github.kittinunf.reactiveandroid.MutableProperty
import com.github.kittinunf.reactiveandroid.createMainThreadMutableProperty

//================================================================================
// Properties 
//================================================================================


val CheckedTextView.rx_checkMarkDrawable: MutableProperty<Drawable>
    get() {
        val getter = { checkMarkDrawable }
        val setter: (Drawable) -> Unit = { checkMarkDrawable = it }

        return createMainThreadMutableProperty(getter, setter)
    }

val CheckedTextView.rx_checkMarkTintList: MutableProperty<ColorStateList>
    get() {
        val getter = { checkMarkTintList }
        val setter: (ColorStateList) -> Unit = { checkMarkTintList = it }

        return createMainThreadMutableProperty(getter, setter)
    }

val CheckedTextView.rx_checkMarkTintMode: MutableProperty<PorterDuff.Mode>
    get() {
        val getter = { checkMarkTintMode }
        val setter: (PorterDuff.Mode) -> Unit = { checkMarkTintMode = it }

        return createMainThreadMutableProperty(getter, setter)
    }

val CheckedTextView.rx_checked: MutableProperty<Boolean>
    get() {
        val getter = { isChecked }
        val setter: (Boolean) -> Unit = { isChecked = it }

        return createMainThreadMutableProperty(getter, setter)
    }
