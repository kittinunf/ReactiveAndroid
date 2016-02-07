package com.github.kittinunf.reactiveandroid.widget

import android.graphics.drawable.Drawable
import android.widget.CompoundButton
import com.github.kittinunf.reactiveandroid.MutableProperty
import com.github.kittinunf.reactiveandroid.createMainThreadMutableProperty

//================================================================================
// Properties 
//================================================================================
 
private var _rx_buttonDrawable: MutableProperty<Drawable>? = null

val CompoundButton.rx_buttonDrawable: MutableProperty<Drawable>
    get() {
        val getter = { buttonDrawable }
        val setter: (Drawable) -> Unit = { buttonDrawable = it }
        
        if (_rx_buttonDrawable == null) {
            _rx_buttonDrawable = createMainThreadMutableProperty(getter, setter)
        } else {
            _rx_buttonDrawable!!.value = getter()
        }
        return _rx_buttonDrawable!!
    }

private var _rx_checked: MutableProperty<Boolean>? = null

val CompoundButton.rx_checked: MutableProperty<Boolean>
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


