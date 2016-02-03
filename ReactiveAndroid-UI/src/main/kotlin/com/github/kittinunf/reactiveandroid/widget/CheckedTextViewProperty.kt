package com.github.kittinunf.reactiveandroid.widget

import android.content.res.ColorStateList
import android.graphics.PorterDuff
import android.graphics.drawable.Drawable
import android.widget.CheckedTextView
import com.github.kittinunf.reactiveandroid.MutableProperty
import com.github.kittinunf.reactiveandroid.createMainThreadMutableProperty

//================================================================================
// Properties 
//================================================================================

private var _rx_checkMarkDrawable: MutableProperty<Drawable>? = null

val CheckedTextView.rx_checkMarkDrawable: MutableProperty<Drawable>
    get() {
        val getter = { checkMarkDrawable }
        val setter: (Drawable) -> Unit = { checkMarkDrawable = it }
        
        if (_rx_checkMarkDrawable == null) {
            _rx_checkMarkDrawable = createMainThreadMutableProperty(getter, setter)
        } else {
            _rx_checkMarkDrawable!!.value = getter()
        }
        return _rx_checkMarkDrawable!!
    }

private var _rx_checkMarkTintList: MutableProperty<ColorStateList>? = null

val CheckedTextView.rx_checkMarkTintList: MutableProperty<ColorStateList>
    get() {
        val getter = { checkMarkTintList }
        val setter: (ColorStateList) -> Unit = { checkMarkTintList = it }
        
        if (_rx_checkMarkTintList == null) {
            _rx_checkMarkTintList = createMainThreadMutableProperty(getter, setter)
        } else {
            _rx_checkMarkTintList!!.value = getter()
        }
        return _rx_checkMarkTintList!!
    }

private var _rx_checkMarkTintMode: MutableProperty<PorterDuff.Mode>? = null

val CheckedTextView.rx_checkMarkTintMode: MutableProperty<PorterDuff.Mode>
    get() {
        val getter = { checkMarkTintMode }
        val setter: (PorterDuff.Mode) -> Unit = { checkMarkTintMode = it }
        
        if (_rx_checkMarkTintMode == null) {
            _rx_checkMarkTintMode = createMainThreadMutableProperty(getter, setter)
        } else {
            _rx_checkMarkTintMode!!.value = getter()
        }
        return _rx_checkMarkTintMode!!
    }

private var _rx_checked: MutableProperty<Boolean>? = null

val CheckedTextView.rx_checked: MutableProperty<Boolean>
    get() {
        val getter = { isChecked }
        val setter: (Boolean) -> Unit = { isChecked = it }

        if (_rx_checked == null) {
            _rx_checked = createMainThreadMutableProperty(getter, setter)
        } else {
            _rx_checked!!.value = getter()
        }
        return _rx_checked!!
    }


 
