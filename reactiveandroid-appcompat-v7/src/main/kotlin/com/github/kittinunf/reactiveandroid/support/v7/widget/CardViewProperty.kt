package com.github.kittinunf.reactiveandroid.support.v7.widget

import android.support.v7.widget.CardView
import com.github.kittinunf.reactiveandroid.MutableProperty
import com.github.kittinunf.reactiveandroid.createMainThreadMutableProperty
import com.github.kittinunf.reactiveandroid.view.Padding

//================================================================================
// Properties
//================================================================================

val CardView.rx_radius: MutableProperty<Float>
    get() {
        val getter = { radius }
        val setter: (Float) -> Unit = { radius = it }

        return createMainThreadMutableProperty(getter, setter)
    }

val CardView.rx_cardElevation: MutableProperty<Float>
    get() {
        val getter = { cardElevation }
        val setter: (Float) -> Unit = { cardElevation = it }

        return createMainThreadMutableProperty(getter, setter)
    }

val CardView.rx_maxCardElevation: MutableProperty<Float>
    get() {
        val getter = { maxCardElevation }
        val setter: (Float) -> Unit = { maxCardElevation = it }

        return createMainThreadMutableProperty(getter, setter)
    }


val CardView.rx_preventCornerOverlap: MutableProperty<Boolean>
    get() {
        val getter = { preventCornerOverlap }
        val setter: (Boolean) -> Unit = { preventCornerOverlap = it }

        return createMainThreadMutableProperty(getter, setter)
    }

val CardView.rx_useCompatPadding: MutableProperty<Boolean>
    get() {
        val getter = { useCompatPadding }
        val setter: (Boolean) -> Unit = { useCompatPadding = it }

        return createMainThreadMutableProperty(getter, setter)
    }

val CardView.rx_contentPadding: MutableProperty<Padding>
    get() {
        val getter = { Padding(contentPaddingLeft, contentPaddingTop, contentPaddingRight, contentPaddingBottom) }
        val setter: (Padding) -> Unit = { setContentPadding(it.start, it.top, it.end, it.bottom) }

        return createMainThreadMutableProperty(getter, setter)
    }
