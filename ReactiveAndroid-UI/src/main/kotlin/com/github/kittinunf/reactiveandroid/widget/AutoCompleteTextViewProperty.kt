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

private var _rx_dropdownBackground: MutableProperty<Drawable>? = null

val AutoCompleteTextView.rx_dropdownBackground: MutableProperty<Drawable>
    get() {
        val getter = { dropDownBackground }
        val setter: (Drawable) -> Unit = { setDropDownBackgroundDrawable(it) }
        
        if (_rx_dropdownBackground == null) {
            _rx_dropdownBackground = createMainThreadMutableProperty(getter, setter)
        } else {
            _rx_dropdownBackground!!.value = getter()
        }
        return _rx_dropdownBackground!!
    }

private var _rx_listSelection: MutableProperty<Int>? = null

val AutoCompleteTextView.rx_listSelection: MutableProperty<Int>
    get() {
        val getter = { listSelection }
        val setter: (Int) -> Unit = { listSelection = it }
        
        if (_rx_listSelection == null) {
            _rx_listSelection = createMainThreadMutableProperty(getter, setter)
        } else {
            _rx_listSelection!!.value = getter()
        }
        return _rx_listSelection!!
    }

private var _rx_threshold: MutableProperty<Int>? = null

val AutoCompleteTextView.rx_threshold: MutableProperty<Int>
    get() {
        val getter = { threshold }
        val setter: (Int) -> Unit = { threshold = it }
        
        if (_rx_threshold == null) {
            _rx_threshold = createMainThreadMutableProperty(getter, setter)
        } else {
            _rx_threshold!!.value = getter()
        }
        return _rx_threshold!!
    }

private var _rx_validator: MutableProperty<AutoCompleteTextView.Validator>? = null

val AutoCompleteTextView.rx_validator: MutableProperty<AutoCompleteTextView.Validator>
    get() {
        val getter = { validator }
        val setter: (AutoCompleteTextView.Validator) -> Unit = { validator = it }

        if (_rx_validator == null) {
            _rx_validator = createMainThreadMutableProperty(getter, setter)
        } else {
            _rx_validator!!.value = getter()
        }
        return _rx_validator!!
    }
