package com.github.kittinunf.reactiveandroid.widget

import android.widget.RadioGroup
import com.github.kittinunf.reactiveandroid.MutableProperty
import com.github.kittinunf.reactiveandroid.createMainThreadMutableProperty

//================================================================================
// Properties 
//================================================================================

val RadioGroup.rx_check: MutableProperty<Int>
    get() {
        val getter = { checkedRadioButtonId }
        val setter: (Int) -> Unit = { check(it) }

        return createMainThreadMutableProperty(getter, setter)
    }
