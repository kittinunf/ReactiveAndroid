package com.github.kittinunf.reactiveandroid.widget

import android.content.res.ColorStateList
import android.graphics.Typeface
import android.widget.TextView
import com.github.kittinunf.reactiveandroid.MutableProperty
import com.github.kittinunf.reactiveandroid.createMainThreadMutableProperty

//================================================================================
// Properties
//================================================================================

val TextView.rx_error: MutableProperty<CharSequence>
    get() {
        val getter = { error }
        val setter: (CharSequence) -> Unit = { error = it }

        return createMainThreadMutableProperty(getter, setter)
    }

val TextView.rx_hint: MutableProperty<CharSequence>
    get() {
        val getter = { hint }
        val setter: (CharSequence) -> Unit = { hint = it }

        return createMainThreadMutableProperty(getter, setter)
    }

val TextView.rx_text: MutableProperty<String>
    get() {
        val getter = { text.toString() }
        val setter: (String) -> Unit = { text = it }

        return createMainThreadMutableProperty(getter, setter)
    }

val TextView.rx_textColor: MutableProperty<Int>
    get() {
        val getter = { currentTextColor }
        val setter: (Int) -> Unit = { setTextColor(it) }

        return createMainThreadMutableProperty(getter, setter)
    }

val TextView.rx_textColors: MutableProperty<ColorStateList>
    get() {
        val getter = { textColors }
        val setter: (ColorStateList) -> Unit = { setTextColor(it) }

        return createMainThreadMutableProperty(getter, setter)
    }

val TextView.rx_typeface: MutableProperty<Typeface>
    get() {
        val getter = { typeface }
        val setter: (Typeface) -> Unit = { typeface = it }

        return createMainThreadMutableProperty(getter, setter)
    }
