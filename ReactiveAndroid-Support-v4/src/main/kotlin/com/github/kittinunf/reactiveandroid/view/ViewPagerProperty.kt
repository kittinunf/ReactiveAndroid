package com.github.kittinunf.reactiveandroid.view

import android.support.v4.view.ViewPager
import com.github.kittinunf.reactiveandroid.MutableProperty
import com.github.kittinunf.reactiveandroid.createMainThreadMutableProperty

//================================================================================
// Properties
//================================================================================

private var _rx_currentItem: MutableProperty<Int>? = null

val ViewPager.rx_currentItem: MutableProperty<Int>
    get() {
        val getter = { currentItem }
        val setter: (Int) -> Unit = { currentItem = it }

        if (_rx_currentItem == null) {
            _rx_currentItem = createMainThreadMutableProperty(getter, setter)
        } else {
            _rx_currentItem!!.value = getter()
        }
        return _rx_currentItem!!
    }
