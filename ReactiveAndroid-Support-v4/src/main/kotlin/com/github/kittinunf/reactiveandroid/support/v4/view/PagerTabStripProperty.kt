package com.github.kittinunf.reactiveandroid.support.v4.view

import android.support.v4.view.PagerTabStrip
import com.github.kittinunf.reactiveandroid.MutableProperty
import com.github.kittinunf.reactiveandroid.createMainThreadMutableProperty

//================================================================================
// Properties 
//================================================================================

private var _rx_drawFullUnderline: MutableProperty<Boolean>? = null

val PagerTabStrip.rx_drawFullUnderline: MutableProperty<Boolean>
    get() {
        val getter = { drawFullUnderline }
        val setter: (Boolean) -> Unit = { drawFullUnderline = it }
        
        if (_rx_drawFullUnderline == null) {
            _rx_drawFullUnderline = createMainThreadMutableProperty(getter, setter)
        } else {
            _rx_drawFullUnderline!!.value = getter()
        }
        return _rx_drawFullUnderline!!
    }

private var _rx_tabIndicatorColor: MutableProperty<Int>? = null

val PagerTabStrip.rx_tabIndicatorColor: MutableProperty<Int>
    get() {
        val getter = { tabIndicatorColor }
        val setter: (Int) -> Unit = { tabIndicatorColor = it }
        
        if (_rx_tabIndicatorColor == null) {
            _rx_tabIndicatorColor = createMainThreadMutableProperty(getter, setter)
        } else {
            _rx_tabIndicatorColor!!.value = getter()
        }
        return _rx_tabIndicatorColor!!
    }
 
