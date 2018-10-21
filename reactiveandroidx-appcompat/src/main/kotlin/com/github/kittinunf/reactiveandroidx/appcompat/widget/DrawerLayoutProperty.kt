package com.github.kittinunf.reactiveandroidx.appcompat.widget

import android.graphics.drawable.Drawable
import androidx.drawerlayout.widget.DrawerLayout
import com.github.kittinunf.reactiveandroid.MutableProperty
import com.github.kittinunf.reactiveandroid.createMainThreadMutableProperty

//================================================================================
// Properties
//================================================================================

val DrawerLayout.rx_drawerElevation: MutableProperty<Float>
    get() {
        val getter = { drawerElevation }
        val setter: (Float) -> Unit = { drawerElevation = it }

        return createMainThreadMutableProperty(getter, setter)
    }

val DrawerLayout.rx_statusBarBackground: MutableProperty<Drawable>
    get() {
        val getter = { statusBarBackgroundDrawable!! }
        val setter: (Drawable) -> Unit = { setStatusBarBackground(it) }

        return createMainThreadMutableProperty(getter, setter)
    }
