package com.github.kittinunf.reactiveandroid.view

import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import android.view.View
import com.github.kittinunf.reactiveandroid.MutableProperty
import com.github.kittinunf.reactiveandroid.createMainThreadMutableProperty

//================================================================================
// Properties
//================================================================================

val View.rx_activated: MutableProperty<Boolean>
    get() {
        val getter = { isActivated }
        val setter: (Boolean) -> Unit = { isActivated = it }

        return createMainThreadMutableProperty(getter, setter)
    }

val View.rx_alpha: MutableProperty<Float>
    get() {
        val getter = { alpha }
        val setter: (Float) -> Unit = { alpha = it }

        return createMainThreadMutableProperty(getter, setter)
    }

val View.rx_background: MutableProperty<Drawable>
    get() {
        val getter = { background }
        val setter: (Drawable) -> Unit = { background = it }

        return createMainThreadMutableProperty(getter, setter)
    }

val View.rx_backgroundColor: MutableProperty<Int>
    get() {
        val getter = {
            val colorDrawable = background as ColorDrawable
            colorDrawable.color
        }
        val setter: (Int) -> Unit = { setBackgroundColor(it) }

        return createMainThreadMutableProperty(getter, setter)
    }

val View.rx_clickable: MutableProperty<Boolean>
    get() {
        val getter = { isClickable }
        val setter: (Boolean) -> Unit = { isClickable = it }

        return createMainThreadMutableProperty(getter, setter)
    }

val View.rx_enabled: MutableProperty<Boolean>
    get() {
        val getter = { isEnabled }
        val setter: (Boolean) -> Unit = { isEnabled = it }

        return createMainThreadMutableProperty(getter, setter)
    }

val View.rx_focused: MutableProperty<Boolean>
    get() {
        val getter = { isFocused }
        val setter: (Boolean) -> Unit = {
            if (it) {
                requestFocus()
            } else {
                clearFocus()
            }
        }

        return createMainThreadMutableProperty(getter, setter)
    }

val View.rx_foreground: MutableProperty<Drawable>
    get() {
        val getter = { foreground }
        val setter: (Drawable) -> Unit = { foreground = it }

        return createMainThreadMutableProperty(getter, setter)
    }

val View.rx_longClickable: MutableProperty<Boolean>
    get() {
        val getter = { isLongClickable }
        val setter: (Boolean) -> Unit = { isLongClickable = it }

        return createMainThreadMutableProperty(getter, setter)
    }

data class Padding(val start: Int, val top: Int, val end: Int, val bottom: Int)

val View.rx_padding: MutableProperty<Padding>
    get() {
        val getter = { Padding(paddingStart, paddingTop, paddingEnd, paddingBottom) }
        val setter: (Padding) -> Unit = {
            val (start, top, end, bottom) = it
            setPaddingRelative(start, top, end, bottom)
        }

        return createMainThreadMutableProperty(getter, setter)
    }

val View.rx_pressed: MutableProperty<Boolean>
    get() {
        val getter = { isPressed }
        val setter: (Boolean) -> Unit = { isPressed = it }

        return createMainThreadMutableProperty(getter, setter)
    }

val View.rx_selected: MutableProperty<Boolean>
    get() {
        val getter = { isSelected }
        val setter: (Boolean) -> Unit = { isSelected = it }

        return createMainThreadMutableProperty(getter, setter)
    }

val View.rx_visibility: MutableProperty<Int>
    get() {
        val getter = { visibility }
        val setter: (Int) -> Unit = { visibility = it }

        return createMainThreadMutableProperty(getter, setter)
    }

