package com.github.kittinunf.reactiveandroidx.appcompat.widget

import android.graphics.drawable.Drawable
import androidx.appcompat.widget.Toolbar
import com.github.kittinunf.reactiveandroid.MutableProperty
import com.github.kittinunf.reactiveandroid.createMainThreadMutableProperty

//================================================================================
// Properties
//================================================================================

val Toolbar.rx_logo: MutableProperty<Drawable>
    get() {
        val getter = { logo }
        val setter: (Drawable) -> Unit = { logo = it }

        return createMainThreadMutableProperty(getter, setter)
    }

val Toolbar.rx_navigationIcon: MutableProperty<Drawable?>
    get() {
        val getter = { navigationIcon }
        val setter: (Drawable?) -> Unit = { navigationIcon = it }

        return createMainThreadMutableProperty(getter, setter)
    }

val Toolbar.rx_subtitle: MutableProperty<CharSequence>
    get() {
        val getter = { subtitle }
        val setter: (CharSequence) -> Unit = { subtitle = it }

        return createMainThreadMutableProperty(getter, setter)
    }

val Toolbar.rx_title: MutableProperty<CharSequence>
    get() {
        val getter = { title }
        val setter: (CharSequence) -> Unit = { title = it }

        return createMainThreadMutableProperty(getter, setter)
    }
