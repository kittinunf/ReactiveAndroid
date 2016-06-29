package com.github.kittinunf.reactiveandroid.widget

import android.graphics.drawable.Drawable
import android.view.animation.Interpolator
import android.widget.ProgressBar
import com.github.kittinunf.reactiveandroid.MutableProperty
import com.github.kittinunf.reactiveandroid.createMainThreadMutableProperty

//================================================================================
// Properties 
//================================================================================

val ProgressBar.rx_indeterminateDrawable: MutableProperty<Drawable>
    get() {
        val getter = { indeterminateDrawable }
        val setter: (Drawable) -> Unit = { indeterminateDrawable = it }

        return createMainThreadMutableProperty(getter, setter)
    }

val ProgressBar.rx_interpolator: MutableProperty<Interpolator>
    get() {
        val getter = { interpolator }
        val setter: (Interpolator) -> Unit = { interpolator = it }

        return createMainThreadMutableProperty(getter, setter)
    }

val ProgressBar.rx_max: MutableProperty<Int>
    get() {
        val getter = { max }
        val setter: (Int) -> Unit = { max = it }

        return createMainThreadMutableProperty(getter, setter)
    }

val ProgressBar.rx_progress: MutableProperty<Int>
    get() {
        val getter = { progress }
        val setter: (Int) -> Unit = { progress = it }

        return createMainThreadMutableProperty(getter, setter)
    }

val ProgressBar.rx_progressDrawable: MutableProperty<Drawable>
    get() {
        val getter = { progressDrawable }
        val setter: (Drawable) -> Unit = { progressDrawable = it }

        return createMainThreadMutableProperty(getter, setter)
    }

val ProgressBar.rx_secondaryProgress: MutableProperty<Int>
    get() {
        val getter = { secondaryProgress }
        val setter: (Int) -> Unit = { secondaryProgress = it }

        return createMainThreadMutableProperty(getter, setter)
    }
