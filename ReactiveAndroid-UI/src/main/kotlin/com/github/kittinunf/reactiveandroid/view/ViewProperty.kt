package com.github.kittinunf.reactiveandroid.view

import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import android.view.View
import com.github.kittinunf.reactiveandroid.MutableProperty
import com.github.kittinunf.reactiveandroid.createMainThreadMutableProperty

//================================================================================
// Properties
//================================================================================

private var _rx_activated: MutableProperty<Boolean>? = null

val View.rx_activated: MutableProperty<Boolean>
    get() {
        val getter = { isActivated }
        val setter: (Boolean) -> Unit = { isActivated = it }

        if (_rx_activated == null) {
            _rx_activated = createMainThreadMutableProperty(getter, setter)
        } else {
            _rx_activated!!.value = getter()
        }
        return _rx_activated!!
    }

private var _rx_alpha: MutableProperty<Float>? = null

val View.rx_alpha: MutableProperty<Float>
    get() {
        val getter = { alpha }
        val setter: (Float) -> Unit = { alpha = it }

        if (_rx_alpha == null) {
            _rx_alpha = createMainThreadMutableProperty(getter, setter)
        } else {
            _rx_alpha!!.value = getter()
        }
        return _rx_alpha!!
    }

private var _rx_background: MutableProperty<Drawable>? = null

val View.rx_background: MutableProperty<Drawable>
    get() {
        val getter = { background }
        val setter: (Drawable) -> Unit = { background = it }

        if (_rx_background == null) {
            _rx_background = createMainThreadMutableProperty(getter, setter)
        } else {
            _rx_background!!.value = getter()
        }
        return _rx_background!!
    }

private var _rx_backgroundColor: MutableProperty<Int>? = null

val View.rx_backgroundColor: MutableProperty<Int>
    get() {
        val getter = {
            val colorDrawable = background as ColorDrawable
            colorDrawable.color
        }
        val setter: (Int) -> Unit = { setBackgroundColor(it) }

        if (_rx_backgroundColor == null) {
            _rx_backgroundColor = createMainThreadMutableProperty(getter, setter)
        } else {
            _rx_backgroundColor!!.value = getter()
        }
        return _rx_backgroundColor!!
    }

private var _rx_clickable: MutableProperty<Boolean>? = null

val View.rx_clickable: MutableProperty<Boolean>
    get() {
        val getter = { isClickable }
        val setter: (Boolean) -> Unit = { isClickable = it }

        if (_rx_clickable == null) {
            _rx_clickable = createMainThreadMutableProperty(getter, setter)
        } else {
            _rx_clickable!!.value = getter()
        }
        return _rx_clickable!!
    }

private var _rx_enabled: MutableProperty<Boolean>? = null

val View.rx_enabled: MutableProperty<Boolean>
    get() {
        val getter = { isEnabled }
        val setter: (Boolean) -> Unit = {
            isEnabled = it
        }
        
        if (_rx_enabled == null) {
            _rx_enabled = createMainThreadMutableProperty(getter, setter)
        } else {
            _rx_enabled!!.value = getter()
        }
        return _rx_enabled!!
    }

private var _rx_focused: MutableProperty<Boolean>? = null

val View.rx_focused: MutableProperty<Boolean>
    get() {
        val getter = { isFocused }
        val setter: (Boolean) -> Unit = { requestFocus() }

        if (_rx_focused == null) {
            _rx_focused = createMainThreadMutableProperty(getter, setter)
        } else {
            _rx_focused!!.value = getter()
        }
        return _rx_focused!!
    }

private var _rx_foreground: MutableProperty<Drawable>? = null

val View.rx_foreground: MutableProperty<Drawable>
    get() {
        val getter = { foreground }
        val setter: (Drawable) -> Unit = { foreground = it }
        
        if (_rx_foreground == null) {
            _rx_foreground = createMainThreadMutableProperty(getter, setter)
        } else {
            _rx_foreground!!.value = getter()
        }
        return _rx_foreground!!
    }

private var _rx_longClickable: MutableProperty<Boolean>? = null

val View.rx_longClickable: MutableProperty<Boolean>
    get() {
        val getter = { isLongClickable }
        val setter: (Boolean) -> Unit = { isLongClickable = it }
        
        if (_rx_longClickable == null) {
            _rx_longClickable = createMainThreadMutableProperty(getter, setter)
        } else {
            _rx_longClickable!!.value = getter()
        }
        return _rx_longClickable!!
    }

data class Padding(val start: Int, val top: Int, val end: Int, val bottom: Int)

private var _rx_padding: MutableProperty<Padding>? = null

val View.rx_padding: MutableProperty<Padding>
    get() {
        val getter = { Padding(paddingStart, paddingTop, paddingEnd, paddingBottom) }
        val setter: (Padding) -> Unit = {
            val (start, top, end, bottom) = it
            setPaddingRelative(start, top, end, bottom)
        }
        
        if (_rx_padding == null) {
            _rx_padding = createMainThreadMutableProperty(getter, setter)
        } else {
            _rx_padding!!.value = getter()
        }
        return _rx_padding!!
    }

private var _rx_pressed: MutableProperty<Boolean>? = null

val View.rx_pressed: MutableProperty<Boolean>
    get() {
        val getter = { isPressed }
        val setter: (Boolean) -> Unit = { isPressed = it }

        if (_rx_pressed == null) {
            _rx_pressed = createMainThreadMutableProperty(getter, setter)
        } else {
            _rx_pressed!!.value = getter()
        }
        return _rx_pressed!!
    }

private var _rx_selected: MutableProperty<Boolean>? = null

val View.rx_selected: MutableProperty<Boolean>
    get() {
        val getter = { isSelected }
        val setter: (Boolean) -> Unit = { isSelected = it }

        if (_rx_selected == null) {
            _rx_selected = createMainThreadMutableProperty(getter, setter)
        } else {
            _rx_selected!!.value = getter()
        }
        return _rx_selected!!
    }

private var _rx_visibility: MutableProperty<Int>? = null

val View.rx_visibility: MutableProperty<Int>
    get() {
        val getter = { visibility }
        val setter: (Int) -> Unit = { visibility = it }

        if (_rx_visibility == null) {
            _rx_visibility = createMainThreadMutableProperty(getter, setter)
        } else {
            _rx_visibility!!.value = getter()
        }
        return _rx_visibility!!
    }

