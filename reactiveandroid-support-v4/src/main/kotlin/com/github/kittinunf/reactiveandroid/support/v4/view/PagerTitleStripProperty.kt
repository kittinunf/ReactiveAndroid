package com.github.kittinunf.reactiveandroid.support.v4.view

import android.support.v4.view.PagerTitleStrip
import com.github.kittinunf.reactiveandroid.MutableProperty
import com.github.kittinunf.reactiveandroid.createMainThreadMutableProperty

//================================================================================
// Properties
//================================================================================

private var _rx_textSpacing: MutableProperty<Int>? = null

val PagerTitleStrip.rx_textSpacing: MutableProperty<Int>
    get() {
        val getter = { textSpacing }
        val setter: (Int) -> Unit = { textSpacing = it }
        
        if (_rx_textSpacing == null) {
            _rx_textSpacing = createMainThreadMutableProperty(getter, setter)
        } else {
            _rx_textSpacing!!.value = getter()
        }
        return _rx_textSpacing!!
    }


 
