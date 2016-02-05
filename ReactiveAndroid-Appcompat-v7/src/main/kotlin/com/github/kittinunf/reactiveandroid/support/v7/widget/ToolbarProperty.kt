package com.github.kittinunf.reactiveandroid.support.v7.widget

import android.graphics.drawable.Drawable
import android.support.v7.widget.Toolbar
import com.github.kittinunf.reactiveandroid.MutableProperty
import com.github.kittinunf.reactiveandroid.createMainThreadMutableProperty

//================================================================================
// Properties
//================================================================================

private var _rx_logo: MutableProperty<Drawable>? = null

val Toolbar.rx_logo: MutableProperty<Drawable>
    get() {
        val getter = { logo }
        val setter: (Drawable) -> Unit = { logo = it }

        if (_rx_logo == null) {
            _rx_logo = createMainThreadMutableProperty(getter, setter)
        } else {
            _rx_logo!!.value = getter()
        }
        return _rx_logo!!
    }

private var _rx_navigationIcon: MutableProperty<Drawable?>? = null

val Toolbar.rx_navigationIcon: MutableProperty<Drawable?>
    get() {
        val getter = { navigationIcon }
        val setter: (Drawable?) -> Unit = { navigationIcon = it }

        if (_rx_navigationIcon == null) {
            _rx_navigationIcon = createMainThreadMutableProperty(getter, setter)
        } else {
            _rx_navigationIcon!!.value = getter()
        }
        return _rx_navigationIcon!!
    }

private var _rx_subtitle: MutableProperty<CharSequence>? = null

val Toolbar.rx_subtitle: MutableProperty<CharSequence>
    get() {
        val getter = { subtitle }
        val setter: (CharSequence) -> Unit = { subtitle = it }

        if (_rx_subtitle == null) {
            _rx_subtitle = createMainThreadMutableProperty(getter, setter)
        } else {
            _rx_subtitle!!.value = getter()
        }
        return _rx_subtitle!!
    }

private var _rx_title: MutableProperty<CharSequence>? = null

val Toolbar.rx_title: MutableProperty<CharSequence>
    get() {
        val getter = { title }
        val setter: (CharSequence) -> Unit = { title = it }

        if (_rx_title == null) {
            _rx_title = createMainThreadMutableProperty(getter, setter)
        } else {
            _rx_title!!.value = getter()
        }
        return _rx_title!!
    }


 
