package com.github.kittinunf.reactiveandroid.app

import android.app.ActionBar
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
 
