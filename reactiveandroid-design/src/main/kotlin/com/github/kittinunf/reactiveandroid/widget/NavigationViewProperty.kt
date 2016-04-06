package com.github.kittinunf.reactiveandroid.widget

import android.content.res.ColorStateList
import android.graphics.drawable.Drawable
import android.support.design.widget.NavigationView
import com.github.kittinunf.reactiveandroid.MutableProperty
import com.github.kittinunf.reactiveandroid.createMainThreadMutableProperty

//================================================================================
// Properties 
//================================================================================

val NavigationView.rx_itemBackground: MutableProperty<Drawable?>
    get() {
        val getter = { itemBackground }
        val setter: (Drawable?) -> Unit = { itemBackground = it }

        return createMainThreadMutableProperty(getter, setter)
    }

val NavigationView.rx_itemIconTintList: MutableProperty<ColorStateList?>
    get() {
        val getter = { itemIconTintList }
        val setter: (ColorStateList?) -> Unit = { itemIconTintList = it }

        return createMainThreadMutableProperty(getter, setter)
    }

val NavigationView.rx_itemTextColor: MutableProperty<ColorStateList?>
    get() {
        val getter = { itemTextColor }
        val setter: (ColorStateList?) -> Unit = { itemTextColor = it }

        return createMainThreadMutableProperty(getter, setter)
    }
