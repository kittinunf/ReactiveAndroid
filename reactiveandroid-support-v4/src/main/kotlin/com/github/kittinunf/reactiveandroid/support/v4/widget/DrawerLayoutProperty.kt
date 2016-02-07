package com.github.kittinunf.reactiveandroid.support.v4.widget

import android.graphics.drawable.Drawable
import android.support.v4.widget.DrawerLayout
import com.github.kittinunf.reactiveandroid.MutableProperty
import com.github.kittinunf.reactiveandroid.createMainThreadMutableProperty

//================================================================================
// Properties
//================================================================================

private var _rx_drawerElevation: MutableProperty<Float>? = null

val DrawerLayout.rx_drawerElevation: MutableProperty<Float>
    get() {
        val getter = { drawerElevation }
        val setter: (Float) -> Unit = { drawerElevation = it }
        
        if (_rx_drawerElevation == null) {
            _rx_drawerElevation = createMainThreadMutableProperty(getter, setter)
        } else {
            _rx_drawerElevation!!.value = getter()
        }
        return _rx_drawerElevation!!
    }

private var _rx_statusBarBackground: MutableProperty<Drawable>? = null

val DrawerLayout.rx_statusBarBackground: MutableProperty<Drawable>
    get() {
        val getter = { statusBarBackgroundDrawable }
        val setter: (Drawable) -> Unit = { setStatusBarBackground(it) }
        
        if (_rx_statusBarBackground == null) {
            _rx_statusBarBackground = createMainThreadMutableProperty(getter, setter)
        } else {
            _rx_statusBarBackground!!.value = getter()
        }
        return _rx_statusBarBackground!!
    }
 
