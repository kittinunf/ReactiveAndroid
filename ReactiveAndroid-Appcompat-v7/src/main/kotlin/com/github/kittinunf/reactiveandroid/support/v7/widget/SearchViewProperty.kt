package com.github.kittinunf.reactiveandroid.support.v7.widget

import android.support.v4.widget.CursorAdapter
import android.support.v7.widget.SearchView
import com.github.kittinunf.reactiveandroid.MutableProperty
import com.github.kittinunf.reactiveandroid.createMainThreadMutableProperty

//================================================================================
// Properties
//================================================================================

data class SearchViewQuery(val text: CharSequence, val submit: Boolean)

private var _rx_query: MutableProperty<SearchViewQuery>? = null

val SearchView.rx_query: MutableProperty<SearchViewQuery>
    get() {
        val getter = { SearchViewQuery(query, false) }
        val setter: (SearchViewQuery) -> Unit = { setQuery(it.text, it.submit) }

        if (_rx_query == null) {
            _rx_query = createMainThreadMutableProperty(getter, setter)
        } else {
            _rx_query!!.value = getter()
        }
        return _rx_query!!
    }

private var _rx_queryRequirementEnabled: MutableProperty<Boolean>? = null

val SearchView.rx_queryRequirementEnabled: MutableProperty<Boolean>
    get() {
        val getter = { isQueryRefinementEnabled }
        val setter: (Boolean) -> Unit = { isQueryRefinementEnabled = it }

        if (_rx_queryRequirementEnabled == null) {
            _rx_queryRequirementEnabled = createMainThreadMutableProperty(getter, setter)
        } else {
            _rx_queryRequirementEnabled!!.value = getter()
        }
        return _rx_queryRequirementEnabled!!
    }

private var _rx_submitButtonEnabled: MutableProperty<Boolean>? = null

val SearchView.rx_submitButtonEnabled: MutableProperty<Boolean>
    get() {
        val getter = { isSubmitButtonEnabled }
        val setter: (Boolean) -> Unit = { isSubmitButtonEnabled = it }

        if (_rx_submitButtonEnabled == null) {
            _rx_submitButtonEnabled = createMainThreadMutableProperty(getter, setter)
        } else {
            _rx_submitButtonEnabled!!.value = getter()
        }
        return _rx_submitButtonEnabled!!
    }

private var _rx_suggestionsAdapter: MutableProperty<CursorAdapter>? = null

val SearchView.rx_suggestionsAdapter: MutableProperty<CursorAdapter>
    get() {
        val getter = { suggestionsAdapter }
        val setter: (CursorAdapter) -> Unit = { suggestionsAdapter = it }

        if (_rx_suggestionsAdapter == null) {
            _rx_suggestionsAdapter = createMainThreadMutableProperty(getter, setter)
        } else {
            _rx_suggestionsAdapter!!.value = getter()
        }
        return _rx_suggestionsAdapter!!
    }

 
