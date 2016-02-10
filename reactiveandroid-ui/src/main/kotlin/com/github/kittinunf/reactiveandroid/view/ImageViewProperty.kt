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

val ImageView.rx_baseline: MutableProperty<Int>
    get() {
        val getter = { baseline }
        val setter: (Int) -> Unit = { baseline = it }

        return createMainThreadMutableProperty(getter, setter)
    }

val ImageView.rx_drawable: MutableProperty<Drawable>
    get() {
        val getter = { drawable }
        val setter: (Drawable) -> Unit = { setImageDrawable(it) }

        return createMainThreadMutableProperty(getter, setter)
    }

val ImageView.rx_imageAlpha: MutableProperty<Int>
    get() {
        val getter = { imageAlpha }
        val setter: (Int) -> Unit = { imageAlpha = it }

        return createMainThreadMutableProperty(getter, setter)
    }

val ImageView.rx_imageMatrix: MutableProperty<Matrix>
    get() {
        val getter = { imageMatrix }
        val setter: (Matrix) -> Unit = { imageMatrix = it }

        return createMainThreadMutableProperty(getter, setter)
    }

val ImageView.rx_adjustViewBounds: MutableProperty<Boolean>
    get() {
        val getter = { adjustViewBounds }
        val setter: (Boolean) -> Unit = { adjustViewBounds = it }
        return createMainThreadMutableProperty(getter, setter)
    }

val ImageView.rx_cropToPadding: MutableProperty<Boolean>
    get() {
        val getter = { cropToPadding }
        val setter: (Boolean) -> Unit = { cropToPadding = it }

        return createMainThreadMutableProperty(getter, setter)
    }

val ImageView.rx_maxWidth: MutableProperty<Int>
    get() {
        val getter = { maxWidth }
        val setter: (Int) -> Unit = { maxWidth = it }

        return createMainThreadMutableProperty(getter, setter)
    }

val ImageView.rx_maxHeight: MutableProperty<Int>
    get() {
        val getter = { maxHeight }
        val setter: (Int) -> Unit = { maxHeight = it }

        return createMainThreadMutableProperty(getter, setter)
    }

val ImageView.rx_scaleType: MutableProperty<ImageView.ScaleType>
    get() {
        val getter = { scaleType }
        val setter: (ImageView.ScaleType) -> Unit = { scaleType = it }

        return createMainThreadMutableProperty(getter, setter)
    }

val ImageView.rx_imageTintMode: MutableProperty<PorterDuff.Mode>
    get() {
        val getter = { imageTintMode }
        val setter: (PorterDuff.Mode) -> Unit = { imageTintMode = it }

        return createMainThreadMutableProperty(getter, setter)
    }

val ImageView.rx_imageTintList: MutableProperty<ColorStateList>
    get() {
        val getter = { imageTintList }
        val setter: (ColorStateList) -> Unit = { imageTintList = it }

        return createMainThreadMutableProperty(getter, setter)
    }
