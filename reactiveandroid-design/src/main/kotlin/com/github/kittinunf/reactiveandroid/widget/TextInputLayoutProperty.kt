package com.github.kittinunf.reactiveandroid.widget

import android.support.design.widget.TextInputLayout
import com.github.kittinunf.reactiveandroid.MutableProperty
import com.github.kittinunf.reactiveandroid.createMainThreadMutableProperty

//================================================================================
// Properties
//================================================================================

val TextInputLayout.rx_errorEnabled: MutableProperty<Boolean>
    get() {
        val getter = { isErrorEnabled }
        val setter: (Boolean) -> Unit = { isErrorEnabled = it }

        return createMainThreadMutableProperty(getter, setter)
    }

val TextInputLayout.rx_hintAnimationEnabled: MutableProperty<Boolean>
    get() {
        val getter = { isHintAnimationEnabled }
        val setter: (Boolean) -> Unit = { isHintAnimationEnabled = it }

        return createMainThreadMutableProperty(getter, setter)
    }

val TextInputLayout.rx_error: MutableProperty<String?>
    get() {
        val getter = { error.toString() }
        val setter: (String?) -> Unit = { error = it }

        return createMainThreadMutableProperty(getter, setter)
    }

val TextInputLayout.rx_hint: MutableProperty<String?>
    get() {
        val getter = { hint.toString() }
        val setter: (String?) -> Unit = { hint = it }

        return createMainThreadMutableProperty(getter, setter)
    }
