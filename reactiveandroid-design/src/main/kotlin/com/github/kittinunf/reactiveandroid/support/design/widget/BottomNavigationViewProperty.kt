package com.github.kittinunf.reactiveandroid.support.design.widget

import android.content.res.ColorStateList
import android.support.design.widget.BottomNavigationView
import com.github.kittinunf.reactiveandroid.MutableProperty
import com.github.kittinunf.reactiveandroid.createMainThreadMutableProperty

//================================================================================
// Properties
//================================================================================

val BottomNavigationView.rx_itemBackgroundResource: MutableProperty<Int>
    get() {
        val getter = { itemBackgroundResource }
        val setter: (Int) -> Unit = { itemBackgroundResource = it }

        return createMainThreadMutableProperty(getter, setter)
    }

val BottomNavigationView.rx_itemIconTintList: MutableProperty<ColorStateList?>
    get() {
        val getter = { itemIconTintList }
        val setter: (ColorStateList?) -> Unit = { itemIconTintList = it }

        return createMainThreadMutableProperty(getter, setter)
    }

val BottomNavigationView.rx_itemTextColor: MutableProperty<ColorStateList?>
    get() {
        val getter = { itemTextColor }
        val setter: (ColorStateList?) -> Unit = { itemTextColor = it }

        return createMainThreadMutableProperty(getter, setter)
    }
