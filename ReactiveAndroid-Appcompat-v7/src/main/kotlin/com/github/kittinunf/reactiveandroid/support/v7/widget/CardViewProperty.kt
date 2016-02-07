package com.github.kittinunf.reactiveandroid.support.v7.widget

import android.support.v7.widget.CardView
import com.github.kittinunf.reactiveandroid.MutableProperty
import com.github.kittinunf.reactiveandroid.createMainThreadMutableProperty
import com.github.kittinunf.reactiveandroid.view.Padding

//================================================================================
// Properties
//================================================================================

private var _rx_radius: MutableProperty<Float>? = null

val CardView.rx_radius: MutableProperty<Float>
    get() {
        val getter = { radius }
        val setter: (Float) -> Unit = { radius = it }
        
        if (_rx_radius == null) {
            _rx_radius = createMainThreadMutableProperty(getter, setter)
        } else {
            _rx_radius!!.value = getter()
        }
        return _rx_radius!!
    }

private var _rx_cardElevation: MutableProperty<Float>? = null

val CardView.rx_cardElevation: MutableProperty<Float>
    get() {
        val getter = { cardElevation }
        val setter: (Float) -> Unit = { cardElevation = it }
        
        if (_rx_cardElevation == null) {
            _rx_cardElevation = createMainThreadMutableProperty(getter, setter)
        } else {
            _rx_cardElevation!!.value = getter()
        }
        return _rx_cardElevation!!
    }

private var _rx_maxCardElevation: MutableProperty<Float>? = null

val CardView.rx_maxCardElevation: MutableProperty<Float>
    get() {
        val getter = { maxCardElevation }
        val setter: (Float) -> Unit = { maxCardElevation = it }

        if (_rx_maxCardElevation == null) {
            _rx_maxCardElevation = createMainThreadMutableProperty(getter, setter)
        } else {
            _rx_maxCardElevation!!.value = getter()
        }
        return _rx_maxCardElevation!!
    }

private var _rx_preventCornerOverlap: MutableProperty<Boolean>? = null

val CardView.rx_preventCornerOverlap: MutableProperty<Boolean>
    get() {
        val getter = { preventCornerOverlap }
        val setter: (Boolean) -> Unit = { preventCornerOverlap = it }
        
        if (_rx_preventCornerOverlap == null) {
            _rx_preventCornerOverlap = createMainThreadMutableProperty(getter, setter)
        } else {
            _rx_preventCornerOverlap!!.value = getter()
        }
        return _rx_preventCornerOverlap!!
    }

private var _rx_useCompatPadding: MutableProperty<Boolean>? = null

val CardView.rx_useCompatPadding: MutableProperty<Boolean>
    get() {
        val getter = { useCompatPadding }
        val setter: (Boolean) -> Unit = { useCompatPadding = it }

        if (_rx_useCompatPadding == null) {
            _rx_useCompatPadding = createMainThreadMutableProperty(getter, setter)
        } else {
            _rx_useCompatPadding!!.value = getter()
        }
        return _rx_useCompatPadding!!
    }

private var _rx_contentPadding: MutableProperty<Padding>? = null

val CardView.rx_contentPadding: MutableProperty<Padding>
    get() {
        val getter = { Padding(contentPaddingLeft, contentPaddingTop, contentPaddingRight, contentPaddingBottom) }
        val setter: (Padding) -> Unit = { setContentPadding(it.start, it.top, it.end, it.bottom) }
        
        if (_rx_contentPadding == null) {
            _rx_contentPadding = createMainThreadMutableProperty(getter, setter)
        } else {
            _rx_contentPadding!!.value = getter()
        }
        return _rx_contentPadding!!
    }



