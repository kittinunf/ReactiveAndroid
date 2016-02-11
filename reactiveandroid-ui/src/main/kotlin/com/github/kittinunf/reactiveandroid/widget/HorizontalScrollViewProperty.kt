package com.github.kittinunf.reactiveandroid.widget

import android.widget.HorizontalScrollView
import com.github.kittinunf.reactiveandroid.MutableProperty
import com.github.kittinunf.reactiveandroid.createMainThreadMutableProperty

//================================================================================
// Properties 
//================================================================================

val HorizontalScrollView.rx_smoothScrollingEnabled: MutableProperty<Boolean>
    get() {
        val getter = { isSmoothScrollingEnabled }
        val setter: (Boolean) -> Unit = { isSmoothScrollingEnabled = it }

        return createMainThreadMutableProperty(getter, setter)
    }

 
