package com.github.kittinunf.reactiveandroidx.appcompat.app

import androidx.appcompat.app.ActionBar
import com.github.kittinunf.reactiveandroid.MutableProperty
import com.github.kittinunf.reactiveandroid.createMainThreadMutableProperty

//================================================================================
// Properties
//================================================================================

val ActionBar.rx_title: MutableProperty<CharSequence?>
    get() {
        val getter = { title }
        val setter: (CharSequence?) -> Unit = { title = it }

        return createMainThreadMutableProperty(getter, setter)
    }