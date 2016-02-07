package com.github.kittinunf.reactiveandroid.widget

import android.content.res.ColorStateList
import android.widget.TextView
import com.github.kittinunf.reactiveandroid.MutableProperty
import com.github.kittinunf.reactiveandroid.createMainThreadMutableProperty

//================================================================================
// Properties
//================================================================================

private var _rx_error: MutableProperty<CharSequence>? = null

val TextView.rx_error: MutableProperty<CharSequence>
    get() {
        val getter = { error }
        val setter: (CharSequence) -> Unit = { error = it }

        if (_rx_error == null) {
            _rx_error = createMainThreadMutableProperty(getter, setter)
        } else {
            _rx_error!!.value = getter()
        }
        return _rx_error!!
    }

private var _rx_hint: MutableProperty<CharSequence>? = null

val TextView.rx_hint: MutableProperty<CharSequence>
    get() {
        val getter = { hint }
        val setter: (CharSequence) -> Unit = { hint = it }

        if (_rx_hint == null) {
            _rx_hint = createMainThreadMutableProperty(getter, setter)
        } else {
            _rx_hint!!.value = getter()
        }
        return _rx_hint!!
    }

private var _rx_text: MutableProperty<String>? = null

val TextView.rx_text: MutableProperty<String>
    get() {
        val getter = { text.toString() }
        val setter: (String) -> Unit = { text = it }

        if (_rx_text == null) {
            _rx_text = createMainThreadMutableProperty(getter, setter)
        } else {
            _rx_text!!.value = getter()
        }
        return _rx_text!!
    }

private var _rx_textColor: MutableProperty<Int>? = null

val TextView.rx_textColor: MutableProperty<Int>
    get() {
        val getter = { currentTextColor }
        val setter: (Int) -> Unit = { setTextColor(it) }

        if (_rx_textColor == null) {
            _rx_textColor = createMainThreadMutableProperty(getter, setter)
        } else {
            _rx_textColor!!.value = getter()
        }
        return _rx_textColor!!
    }

private var _rx_textColors: MutableProperty<ColorStateList>? = null

val TextView.rx_textColors: MutableProperty<ColorStateList>
    get() {
        val getter = { textColors }
        val setter: (ColorStateList) -> Unit = { setTextColor(it) }

        if (_rx_textColors == null) {
            _rx_textColors = createMainThreadMutableProperty(getter, setter)
        } else {
            _rx_textColors!!.value = getter()
        }
        return _rx_textColors!!
    }
