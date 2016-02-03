package com.github.kittinunf.reactiveandroid.widget

import android.widget.RatingBar
import com.github.kittinunf.reactiveandroid.MutableProperty
import com.github.kittinunf.reactiveandroid.createMainThreadMutableProperty

//================================================================================
// Properties 
//================================================================================

private var _rx_numStars: MutableProperty<Int>? = null

val RatingBar.rx_numStars: MutableProperty<Int>
    get() {
        val getter = { numStars }
        val setter: (Int) -> Unit = { numStars = it }
        
        if (_rx_numStars == null) {
            _rx_numStars = createMainThreadMutableProperty(getter, setter)
        } else {
            _rx_numStars!!.value = getter()
        }
        return _rx_numStars!!
    }

private var _rx_rating: MutableProperty<Float>? = null

val RatingBar.rx_rating: MutableProperty<Float>
    get() {
        val getter = { rating }
        val setter: (Float) -> Unit = { rating = it }
        
        if (_rx_rating == null) {
            _rx_rating = createMainThreadMutableProperty(getter, setter)
        } else {
            _rx_rating!!.value = getter()
        }
        return _rx_rating!!
    }

private var _rx_stepSize: MutableProperty<Float>? = null

val RatingBar.rx_stepSize: MutableProperty<Float>
    get() {
        val getter = { stepSize }
        val setter: (Float) -> Unit = { stepSize = it }
        
        if (_rx_stepSize == null) {
            _rx_stepSize = createMainThreadMutableProperty(getter, setter)
        } else {
            _rx_stepSize!!.value = getter()
        }
        return _rx_stepSize!!
    }


