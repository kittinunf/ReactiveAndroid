package com.github.kittinunf.reactiveandroid.widget

import android.widget.RatingBar
import com.github.kittinunf.reactiveandroid.MutableProperty
import com.github.kittinunf.reactiveandroid.createMainThreadMutableProperty

//================================================================================
// Properties 
//================================================================================

val RatingBar.rx_numStars: MutableProperty<Int>
    get() {
        val getter = { numStars }
        val setter: (Int) -> Unit = { numStars = it }

        return createMainThreadMutableProperty(getter, setter)
    }

val RatingBar.rx_rating: MutableProperty<Float>
    get() {
        val getter = { rating }
        val setter: (Float) -> Unit = { rating = it }

        return createMainThreadMutableProperty(getter, setter)
    }

val RatingBar.rx_stepSize: MutableProperty<Float>
    get() {
        val getter = { stepSize }
        val setter: (Float) -> Unit = { stepSize = it }

        return createMainThreadMutableProperty(getter, setter)
    }
