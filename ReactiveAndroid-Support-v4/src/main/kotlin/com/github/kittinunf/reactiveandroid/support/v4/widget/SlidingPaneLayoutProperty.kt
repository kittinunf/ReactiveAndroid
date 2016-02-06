package com.github.kittinunf.reactiveandroid.support.v4.widget

import android.support.v4.widget.SlidingPaneLayout
import com.github.kittinunf.reactiveandroid.MutableProperty
import com.github.kittinunf.reactiveandroid.createMainThreadMutableProperty

//================================================================================
// Properties
//================================================================================

private var _rx_coveredFadeColor: MutableProperty<Int>? = null

val SlidingPaneLayout.rx_coveredFadeColor: MutableProperty<Int>
    get() {
        val getter = { coveredFadeColor }
        val setter: (Int) -> Unit = { coveredFadeColor = it }
        
        if (_rx_coveredFadeColor == null) {
            _rx_coveredFadeColor = createMainThreadMutableProperty(getter, setter)
        } else {
            _rx_coveredFadeColor!!.value = getter()
        }
        return _rx_coveredFadeColor!!
    }

private var _rx_parallaxDistance: MutableProperty<Int>? = null

val SlidingPaneLayout.rx_parallaxDistance: MutableProperty<Int>
    get() {
        val getter = { parallaxDistance }
        val setter: (Int) -> Unit = { parallaxDistance = it }

        if (_rx_parallaxDistance == null) {
            _rx_parallaxDistance = createMainThreadMutableProperty(getter, setter)
        } else {
            _rx_parallaxDistance!!.value = getter()
        }
        return _rx_parallaxDistance!!
    }

private var _rx_sliderFadeColor: MutableProperty<Int>? = null

val SlidingPaneLayout.rx_sliderFadeColor: MutableProperty<Int>
    get() {
        val getter = { sliderFadeColor }
        val setter: (Int) -> Unit = { sliderFadeColor = it }
        
        if (_rx_sliderFadeColor == null) {
            _rx_sliderFadeColor = createMainThreadMutableProperty(getter, setter)
        } else {
            _rx_sliderFadeColor!!.value = getter()
        }
        return _rx_sliderFadeColor!!
    }


 
