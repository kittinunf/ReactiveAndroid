package com.github.kittinunf.reactiveandroid.view

import android.graphics.drawable.Drawable
import android.view.MenuItem
import android.view.View
import com.github.kittinunf.reactiveandroid.MutableProperty
import com.github.kittinunf.reactiveandroid.createMainThreadMutableProperty

//================================================================================
// Properties
//================================================================================

private var _rx_actionView: MutableProperty<View>? = null

val MenuItem.rx_actionView: MutableProperty<View>
    get() {
        val getter = { actionView }
        val setter: (View) -> Unit = { setActionView(it) }

        if (_rx_actionView == null) {
            _rx_actionView = createMainThreadMutableProperty(getter, setter)
        } else {
            _rx_actionView!!.value = getter()
        }
        return _rx_actionView!!
    }

private var _rx_icon: MutableProperty<Drawable>? = null

val MenuItem.rx_icon: MutableProperty<Drawable>
    get() {
        val getter = { icon }
        val setter: (Drawable) -> Unit = { setIcon(it) }

        if (_rx_icon == null) {
            _rx_icon = createMainThreadMutableProperty(getter, setter)
        } else {
            _rx_icon!!.value = getter()
        }
        return _rx_icon!!
    }

private var _rx_checkable: MutableProperty<Boolean>? = null

val MenuItem.rx_checkable: MutableProperty<Boolean>
    get() {
        val getter = { isCheckable }
        val setter: (Boolean) -> Unit = { setCheckable(it) }

        if (_rx_checkable == null) {
            _rx_checkable = createMainThreadMutableProperty(getter, setter)
        } else {
            _rx_checkable!!.value = getter()
        }
        return _rx_checkable!!
    }

private var _rx_checked: MutableProperty<Boolean>? = null

val MenuItem.rx_checked: MutableProperty<Boolean>
    get() {
        val getter = { isChecked }
        val setter: (Boolean) -> Unit = { setChecked(it) }

        if (_rx_checked == null) {
            _rx_checked = createMainThreadMutableProperty(getter, setter)
        } else {
            _rx_checked!!.value = getter()
        }
        return _rx_checked!!
    }

private var _rx_enabled: MutableProperty<Boolean>? = null

val MenuItem.rx_enabled: MutableProperty<Boolean>
    get() {
        val getter = { isEnabled }
        val setter: (Boolean) -> Unit = { setEnabled(it) }

        if (_rx_enabled == null) {
            _rx_enabled = createMainThreadMutableProperty(getter, setter)
        } else {
            _rx_enabled!!.value = getter()
        }
        return _rx_enabled!!
    }

private var _rx_visible: MutableProperty<Boolean>? = null

val MenuItem.rx_visible: MutableProperty<Boolean>
    get() {
        val getter = { isVisible }
        val setter: (Boolean) -> Unit = { setVisible(it) }

        if (_rx_visible == null) {
            _rx_visible = createMainThreadMutableProperty(getter, setter)
        } else {
            _rx_visible!!.value = getter()
        }
        return _rx_visible!!
    }

private var _rx_title: MutableProperty<CharSequence>? = null

val MenuItem.rx_title: MutableProperty<CharSequence>
    get() {
        val getter = { title }
        val setter: (CharSequence) -> Unit = { setTitle(it) }

        if (_rx_title == null) {
            _rx_title = createMainThreadMutableProperty(getter, setter)
        } else {
            _rx_title!!.value = getter()
        }
        return _rx_title!!
    }
