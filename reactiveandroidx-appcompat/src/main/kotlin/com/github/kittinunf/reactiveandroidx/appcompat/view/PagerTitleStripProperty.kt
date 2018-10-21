package com.github.kittinunf.reactiveandroidx.appcompat.view

import androidx.viewpager.widget.PagerTitleStrip
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
