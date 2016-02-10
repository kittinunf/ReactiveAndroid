package com.github.kittinunf.reactiveandroid.support.v4.widget

import android.support.v4.widget.SlidingPaneLayout
import com.github.kittinunf.reactiveandroid.MutableProperty
import com.github.kittinunf.reactiveandroid.createMainThreadMutableProperty

//================================================================================
// Properties
//================================================================================

val SlidingPaneLayout.rx_coveredFadeColor: MutableProperty<Int>
    get() {
        val getter = { coveredFadeColor }
        val setter: (Int) -> Unit = { coveredFadeColor = it }

        return createMainThreadMutableProperty(getter, setter)
    }

val SlidingPaneLayout.rx_parallaxDistance: MutableProperty<Int>
    get() {
        val getter = { parallaxDistance }
        val setter: (Int) -> Unit = { parallaxDistance = it }

        return createMainThreadMutableProperty(getter, setter)
    }

val SlidingPaneLayout.rx_sliderFadeColor: MutableProperty<Int>
    get() {
        val getter = { sliderFadeColor }
        val setter: (Int) -> Unit = { sliderFadeColor = it }

        return createMainThreadMutableProperty(getter, setter)
    }
