package com.github.kittinunf.reactiveandroid.support.v7.app

import android.support.v7.app.ActionBar
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

 
