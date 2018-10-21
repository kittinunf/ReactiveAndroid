package com.github.kittinunf.reactiveandroidx.appcompat.view

import androidx.viewpager.widget.PagerTabStrip
import com.github.kittinunf.reactiveandroid.MutableProperty
import com.github.kittinunf.reactiveandroid.createMainThreadMutableProperty

//================================================================================
// Properties 
//================================================================================

val PagerTabStrip.rx_drawFullUnderline: MutableProperty<Boolean>
    get() {
        val getter = { drawFullUnderline }
        val setter: (Boolean) -> Unit = { drawFullUnderline = it }

        return createMainThreadMutableProperty(getter, setter)
    }

val PagerTabStrip.rx_tabIndicatorColor: MutableProperty<Int>
    get() {
        val getter = { tabIndicatorColor }
        val setter: (Int) -> Unit = { tabIndicatorColor = it }

        return createMainThreadMutableProperty(getter, setter)
    }
