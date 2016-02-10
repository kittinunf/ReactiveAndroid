package com.github.kittinunf.reactiveandroid.widget

import android.graphics.drawable.Drawable
import android.widget.AutoCompleteTextView
import android.widget.Filterable
import android.widget.ListAdapter
import com.github.kittinunf.reactiveandroid.MutableProperty
import com.github.kittinunf.reactiveandroid.createMainThreadMutableProperty

//================================================================================
// Properties 
//================================================================================


val AutoCompleteTextView.rx_dropdownBackground: MutableProperty<Drawable>
    get() {
        val getter = { dropDownBackground }
        val setter: (Drawable) -> Unit = { setDropDownBackgroundDrawable(it) }

        return createMainThreadMutableProperty(getter, setter)
    }

val AutoCompleteTextView.rx_listSelection: MutableProperty<Int>
    get() {
        val getter = { listSelection }
        val setter: (Int) -> Unit = { listSelection = it }

        return createMainThreadMutableProperty(getter, setter)
    }

val AutoCompleteTextView.rx_threshold: MutableProperty<Int>
    get() {
        val getter = { threshold }
        val setter: (Int) -> Unit = { threshold = it }

        return createMainThreadMutableProperty(getter, setter)
    }

val AutoCompleteTextView.rx_validator: MutableProperty<AutoCompleteTextView.Validator>
    get() {
        val getter = { validator }
        val setter: (AutoCompleteTextView.Validator) -> Unit = { validator = it }

        return createMainThreadMutableProperty(getter, setter)
    }

