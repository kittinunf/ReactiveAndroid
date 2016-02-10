package com.github.kittinunf.reactiveandroid.view

import android.graphics.drawable.Drawable
import android.view.MenuItem
import android.view.View
import com.github.kittinunf.reactiveandroid.MutableProperty
import com.github.kittinunf.reactiveandroid.createMainThreadMutableProperty

//================================================================================
// Properties
//================================================================================


val MenuItem.rx_actionView: MutableProperty<View>
    get() {
        val getter = { actionView }
        val setter: (View) -> Unit = { setActionView(it) }

        return createMainThreadMutableProperty(getter, setter)
    }

val MenuItem.rx_icon: MutableProperty<Drawable>
    get() {
        val getter = { icon }
        val setter: (Drawable) -> Unit = { setIcon(it) }

        return createMainThreadMutableProperty(getter, setter)
    }

val MenuItem.rx_checkable: MutableProperty<Boolean>
    get() {
        val getter = { isCheckable }
        val setter: (Boolean) -> Unit = { setCheckable(it) }

        return createMainThreadMutableProperty(getter, setter)
    }

val MenuItem.rx_checked: MutableProperty<Boolean>
    get() {
        val getter = { isChecked }
        val setter: (Boolean) -> Unit = { setChecked(it) }

        return createMainThreadMutableProperty(getter, setter)
    }

val MenuItem.rx_enabled: MutableProperty<Boolean>
    get() {
        val getter = { isEnabled }
        val setter: (Boolean) -> Unit = { setEnabled(it) }

        return createMainThreadMutableProperty(getter, setter)
    }

val MenuItem.rx_visible: MutableProperty<Boolean>
    get() {
        val getter = { isVisible }
        val setter: (Boolean) -> Unit = { setVisible(it) }

        return createMainThreadMutableProperty(getter, setter)
    }

val MenuItem.rx_title: MutableProperty<CharSequence>
    get() {
        val getter = { title }
        val setter: (CharSequence) -> Unit = { setTitle(it) }

        return createMainThreadMutableProperty(getter, setter)
    }
