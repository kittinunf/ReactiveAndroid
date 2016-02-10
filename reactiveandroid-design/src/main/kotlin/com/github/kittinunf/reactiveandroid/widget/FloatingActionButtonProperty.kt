package com.github.kittinunf.reactiveandroid.widget

import android.content.res.ColorStateList
import android.graphics.PorterDuff
import android.graphics.drawable.Drawable
import android.support.design.widget.FloatingActionButton
import com.github.kittinunf.reactiveandroid.MutableProperty
import com.github.kittinunf.reactiveandroid.createMainThreadMutableProperty

//================================================================================
// Properties 
//================================================================================

val FloatingActionButton.rx_background: MutableProperty<Drawable>
    get() {
        val getter = { background }
        val setter: (Drawable) -> Unit = { background = it }

        return createMainThreadMutableProperty(getter, setter)
    }

val FloatingActionButton.rx_backgroundTintList: MutableProperty<ColorStateList?>
    get() {
        val getter = { backgroundTintList }
        val setter: (ColorStateList?) -> Unit = { backgroundTintList = it }

        return createMainThreadMutableProperty(getter, setter)
    }

val FloatingActionButton.rx_backgroundTintMode: MutableProperty<PorterDuff.Mode?>
    get() {
        val getter = { backgroundTintMode }
        val setter: (PorterDuff.Mode?) -> Unit = { backgroundTintMode = it }

        return createMainThreadMutableProperty(getter, setter)
    }

 