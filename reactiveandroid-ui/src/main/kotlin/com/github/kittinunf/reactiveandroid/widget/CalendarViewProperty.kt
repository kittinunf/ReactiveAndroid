package com.github.kittinunf.reactiveandroid.widget

import android.widget.CalendarView
import com.github.kittinunf.reactiveandroid.MutableProperty
import com.github.kittinunf.reactiveandroid.createMainThreadMutableProperty

//================================================================================
// Properties
//================================================================================

val CalendarView.rx_date: MutableProperty<Long>
    get() {
        val getter = { date }
        val setter: (Long) -> Unit = { date = it }

        return createMainThreadMutableProperty(getter, setter)
    }

