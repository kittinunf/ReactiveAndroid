package com.github.kittinunf.reactiveandroid.widget

import android.graphics.drawable.Drawable
import android.view.animation.Interpolator
import android.widget.ProgressBar
import com.github.kittinunf.reactiveandroid.MutableProperty
import com.github.kittinunf.reactiveandroid.createMainThreadMutableProperty

//================================================================================
// Properties 
//================================================================================

private var _rx_indeterminateDrawable: MutableProperty<Drawable>? = null

val ProgressBar.rx_indeterminateDrawable: MutableProperty<Drawable>
    get() {
        val getter = { indeterminateDrawable }
        val setter: (Drawable) -> Unit = { indeterminateDrawable = it }
        
        if (_rx_indeterminateDrawable == null) {
            _rx_indeterminateDrawable = createMainThreadMutableProperty(getter, setter)
        } else {
            _rx_indeterminateDrawable!!.value = getter()
        }
        return _rx_indeterminateDrawable!!
    }
 
private var _rx_interpolator: MutableProperty<Interpolator>? = null

val ProgressBar.rx_interpolator: MutableProperty<Interpolator>
    get() {
        val getter = { interpolator }
        val setter: (Interpolator) -> Unit = { interpolator = it }
        
        if (_rx_interpolator == null) {
            _rx_interpolator = createMainThreadMutableProperty(getter, setter)
        } else {
            _rx_interpolator!!.value = getter()
        }
        return _rx_interpolator!!
    }

private var _rx_max: MutableProperty<Int>? = null

val ProgressBar.rx_max: MutableProperty<Int>
    get() {
        val getter = { max }
        val setter: (Int) -> Unit = { max = it }
        
        if (_rx_max == null) {
            _rx_max = createMainThreadMutableProperty(getter, setter)
        } else {
            _rx_max!!.value = getter()
        }
        return _rx_max!!
    }

private var _rx_progress: MutableProperty<Int>? = null

val ProgressBar.rx_progress: MutableProperty<Int>
    get() {
        val getter = { progress }
        val setter: (Int) -> Unit = { progress = it }
        
        if (_rx_progress == null) {
            _rx_progress = createMainThreadMutableProperty(getter, setter)
        } else {
            _rx_progress!!.value = getter()
        }
        return _rx_progress!!
    }

private var _rx_progressDrawable: MutableProperty<Drawable>? = null

val ProgressBar.rx_progressDrawable: MutableProperty<Drawable>
    get() {
        val getter = { progressDrawable }
        val setter: (Drawable) -> Unit = { progressDrawable = it }
        
        if (_rx_progressDrawable == null) {
            _rx_progressDrawable = createMainThreadMutableProperty(getter, setter)
        } else {
            _rx_progressDrawable!!.value = getter()
        }
        return _rx_progressDrawable!!
    }

private var _rx_secondaryProgress: MutableProperty<Int>? = null

val ProgressBar.rx_secondaryProgress: MutableProperty<Int>
    get() {
        val getter = { secondaryProgress }
        val setter: (Int) -> Unit = { secondaryProgress = it }
        
        if (_rx_secondaryProgress == null) {
            _rx_secondaryProgress = createMainThreadMutableProperty(getter, setter)
        } else {
            _rx_secondaryProgress!!.value = getter()
        }
        return _rx_secondaryProgress!!
    }


