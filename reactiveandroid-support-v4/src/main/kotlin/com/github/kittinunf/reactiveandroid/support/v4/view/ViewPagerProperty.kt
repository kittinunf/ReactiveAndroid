package com.github.kittinunf.reactiveandroid.support.v4.view

import android.support.v4.view.ViewPager
import com.github.kittinunf.reactiveandroid.MutableProperty
import com.github.kittinunf.reactiveandroid.createMainThreadMutableProperty

//================================================================================
// Properties
//================================================================================


val ViewPager.rx_currentItem: MutableProperty<Int>
    get() {
        val getter = { currentItem }
        val setter: (Int) -> Unit = { currentItem = it }

        return createMainThreadMutableProperty(getter, setter)
    }
