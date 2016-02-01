package com.github.kittinunf.reactiveandroid.view

import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import android.view.View
import com.github.kittinunf.reactiveandroid.MutableProperty
import com.github.kittinunf.reactiveandroid.Padding
import com.github.kittinunf.reactiveandroid.createMainThreadMutableProperty

//================================================================================
// Properties
//================================================================================

private var _rx_isActivated: MutableProperty<Boolean>? = null

val View.rx_isActivated: MutableProperty<Boolean>
    get() {
        val getter = { isActivated }
        val setter: (Boolean) -> Unit = { isActivated = it }

        if (_rx_isActivated == null) {
            _rx_isActivated = createMainThreadMutableProperty(getter, setter)
        } else {
            _rx_isActivated!!.value = getter()
        }
        return _rx_isActivated!!
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

private var _rx_isClickable: MutableProperty<Boolean>? = null

val View.rx_isClickable: MutableProperty<Boolean>
    get() {
        val getter = { isClickable }
        val setter: (Boolean) -> Unit = { isClickable = it }

        if (_rx_isClickable == null) {
            _rx_isClickable = createMainThreadMutableProperty(getter, setter)
        } else {
            _rx_isClickable!!.value = getter()
        }
        return _rx_isClickable!!
    }

private var _rx_isEnabled: MutableProperty<Boolean>? = null

val View.rx_isEnabled: MutableProperty<Boolean>
    get() {
        val getter = { isEnabled }
        val setter: (Boolean) -> Unit = { isEnabled = it }
        
        if (_rx_isEnabled == null) {
            _rx_isEnabled = createMainThreadMutableProperty(getter, setter)
        } else {
            _rx_isEnabled!!.value = getter()
        }
        return _rx_isEnabled!!
    }

private var _rx_isFocused: MutableProperty<Boolean>? = null

val View.rx_isFocused: MutableProperty<Boolean>
    get() {
        val getter = { isFocused }
        val setter: (Boolean) -> Unit = { requestFocus() }

        if (_rx_isFocused == null) {
            _rx_isFocused = createMainThreadMutableProperty(getter, setter)
        } else {
            _rx_isFocused!!.value = getter()
        }
        return _rx_isFocused!!
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

private var _rx_isLongClickable: MutableProperty<Boolean>? = null

val View.rx_isLongClickable: MutableProperty<Boolean>
    get() {
        val getter = { isLongClickable }
        val setter: (Boolean) -> Unit = { isLongClickable = it }
        
        if (_rx_isLongClickable == null) {
            _rx_isLongClickable = createMainThreadMutableProperty(getter, setter)
        } else {
            _rx_isLongClickable!!.value = getter()
        }
        return _rx_isLongClickable!!
    }


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

private var _rx_isPressed: MutableProperty<Boolean>? = null

val View.rx_isPressed: MutableProperty<Boolean>
    get() {
        val getter = { isPressed }
        val setter: (Boolean) -> Unit = { isPressed = it }

        if (_rx_isPressed == null) {
            _rx_isPressed = createMainThreadMutableProperty(getter, setter)
        } else {
            _rx_isPressed!!.value = getter()
        }
        return _rx_isPressed!!
    }

private var _rx_isSelected: MutableProperty<Boolean>? = null

val View.rx_isSelected: MutableProperty<Boolean>
    get() {
        val getter = { isSelected }
        val setter: (Boolean) -> Unit = { isSelected = it }

        if (_rx_isSelected == null) {
            _rx_isSelected = createMainThreadMutableProperty(getter, setter)
        } else {
            _rx_isSelected!!.value = getter()
        }
        return _rx_isSelected!!
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

