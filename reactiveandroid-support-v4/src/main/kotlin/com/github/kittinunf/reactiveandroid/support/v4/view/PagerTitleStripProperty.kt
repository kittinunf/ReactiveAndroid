package com.github.kittinunf.reactiveandroid.support.v4.view

import android.support.v4.view.PagerTitleStrip
import com.github.kittinunf.reactiveandroid.MutableProperty
import com.github.kittinunf.reactiveandroid.createMainThreadMutableProperty

//================================================================================
// Properties
//================================================================================

val PagerTitleStrip.rx_textSpacing: MutableProperty<Int>
    get() {
        val getter = { textSpacing }
        val setter: (Int) -> Unit = { textSpacing = it }

        return createMainThreadMutableProperty(getter, setter)
    }
