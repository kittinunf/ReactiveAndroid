package com.github.kittinunf.reactiveandroid.view

import android.content.res.ColorStateList
import android.graphics.Matrix
import android.graphics.PorterDuff
import android.graphics.drawable.Drawable
import android.widget.ImageView
import com.github.kittinunf.reactiveandroid.MutableProperty
import com.github.kittinunf.reactiveandroid.createMainThreadMutableProperty

//================================================================================
// Properties
//================================================================================

private var _rx_drawable: MutableProperty<Drawable>? = null

val ImageView.rx_drawable: MutableProperty<Drawable>
    get() {
        val getter = { drawable }
        val setter: (Drawable) -> Unit = { setImageDrawable(it) }

        if (_rx_drawable == null) {
            _rx_drawable = createMainThreadMutableProperty(getter, setter)
        } else {
            _rx_drawable!!.value = getter()
        }
        return _rx_drawable!!
    }
 
private var _rx_imageAlpha: MutableProperty<Int>? = null

val ImageView.rx_imageAlpha: MutableProperty<Int>
    get() {
        val getter = { imageAlpha }
        val setter: (Int) -> Unit = { imageAlpha = it }
        
        if (_rx_imageAlpha == null) {
            _rx_imageAlpha = createMainThreadMutableProperty(getter, setter)
        } else {
            _rx_imageAlpha!!.value = getter()
        }
        return _rx_imageAlpha!!
    }

private var _rx_imageMatrix: MutableProperty<Matrix>? = null

val ImageView.rx_imageMatrix: MutableProperty<Matrix>
    get() {
        val getter = { imageMatrix }
        val setter: (Matrix) -> Unit = { imageMatrix = it }

        if (_rx_imageMatrix == null) {
            _rx_imageMatrix = createMainThreadMutableProperty(getter, setter)
        } else {
            _rx_imageMatrix!!.value = getter()
        }
        return _rx_imageMatrix!!
    }

private var _rx_adjustViewBounds: MutableProperty<Boolean>? = null

val ImageView.rx_adjustViewBounds: MutableProperty<Boolean>
    get() {
        val getter = { adjustViewBounds }
        val setter: (Boolean) -> Unit = { adjustViewBounds = it }
        
        if (_rx_adjustViewBounds == null) {
            _rx_adjustViewBounds = createMainThreadMutableProperty(getter, setter)
        } else {
            _rx_adjustViewBounds!!.value = getter()
        }
        return _rx_adjustViewBounds!!
    }

private var _rx_cropToPadding: MutableProperty<Boolean>? = null

val ImageView.rx_cropToPadding: MutableProperty<Boolean>
    get() {
        val getter = { cropToPadding }
        val setter: (Boolean) -> Unit = { cropToPadding = it }
        
        if (_rx_cropToPadding == null) {
            _rx_cropToPadding = createMainThreadMutableProperty(getter, setter)
        } else {
            _rx_cropToPadding!!.value = getter()
        }
        return _rx_cropToPadding!!
    }

private var _rx_maxHeight: MutableProperty<Int>? = null

val ImageView.rx_maxHeight: MutableProperty<Int>
    get() {
        val getter = { maxHeight }
        val setter: (Int) -> Unit = { maxHeight = it }
        
        if (_rx_maxHeight == null) {
            _rx_maxHeight = createMainThreadMutableProperty(getter, setter)
        } else {
            _rx_maxHeight!!.value = getter()
        }
        return _rx_maxHeight!!
    }

private var _rx_imageTintMode: MutableProperty<PorterDuff.Mode>? = null

val ImageView.rx_imageTintMode: MutableProperty<PorterDuff.Mode>
    get() {
        val getter = { imageTintMode }
        val setter: (PorterDuff.Mode) -> Unit = { imageTintMode = it }
        
        if (_rx_imageTintMode == null) {
            _rx_imageTintMode = createMainThreadMutableProperty(getter, setter)
        } else {
            _rx_imageTintMode!!.value = getter()
        }
        return _rx_imageTintMode!!
    }

private var _rx_imageTintList: MutableProperty<ColorStateList>? = null

val ImageView.rx_imageTintList: MutableProperty<ColorStateList>
    get() {
        val getter = { imageTintList }
        val setter: (ColorStateList) -> Unit = { imageTintList = it }
        
        if (_rx_imageTintList == null) {
            _rx_imageTintList = createMainThreadMutableProperty(getter, setter)
        } else {
            _rx_imageTintList!!.value = getter()
        }
        return _rx_imageTintList!!
    }