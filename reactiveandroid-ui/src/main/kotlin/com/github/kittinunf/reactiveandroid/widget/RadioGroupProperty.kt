package com.github.kittinunf.reactiveandroid.widget

import android.widget.RadioGroup
import com.github.kittinunf.reactiveandroid.MutableProperty
import com.github.kittinunf.reactiveandroid.createMainThreadMutableProperty

//================================================================================
// Properties 
//================================================================================

private var _rx_check: MutableProperty<Int>? = null

val RadioGroup.rx_check: MutableProperty<Int>
    get() {
        val getter = { checkedRadioButtonId }
        val setter: (Int) -> Unit = { check(it) }
        
        if (_rx_check == null) {
            _rx_check = createMainThreadMutableProperty(getter, setter)
        } else {
            _rx_check!!.value = getter()
        }
        return _rx_check!!
    }
 
