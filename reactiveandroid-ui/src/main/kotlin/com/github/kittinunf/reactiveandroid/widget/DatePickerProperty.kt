package com.github.kittinunf.reactiveandroid.widget

import android.widget.DatePicker
import com.github.kittinunf.reactiveandroid.MutableProperty
import com.github.kittinunf.reactiveandroid.createMainThreadMutableProperty

//================================================================================
// Properties 
//================================================================================

val DatePicker.rx_maxDate: MutableProperty<Long>
    get() {
        val getter = { maxDate }
        val setter: (Long) -> Unit = { maxDate = it }

        return createMainThreadMutableProperty(getter, setter)
    }

val DatePicker.rx_minDate: MutableProperty<Long>
    get() {
        val getter = { minDate }
        val setter: (Long) -> Unit = { minDate = it }

        return createMainThreadMutableProperty(getter, setter)
    }

val DatePicker.rx_spinnersShown: MutableProperty<Boolean>
    get() {
        val getter = { spinnersShown }
        val setter: (Boolean) -> Unit = { spinnersShown = it }

        return createMainThreadMutableProperty(getter, setter)
    }
