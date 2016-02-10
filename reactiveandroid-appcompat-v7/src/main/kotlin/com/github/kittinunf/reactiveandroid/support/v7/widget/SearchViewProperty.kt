package com.github.kittinunf.reactiveandroid.support.v7.widget

import android.support.v4.widget.CursorAdapter
import android.support.v7.widget.SearchView
import com.github.kittinunf.reactiveandroid.MutableProperty
import com.github.kittinunf.reactiveandroid.createMainThreadMutableProperty

//================================================================================
// Properties
//================================================================================

data class SearchViewQuery(val text: CharSequence, val submit: Boolean)

val SearchView.rx_query: MutableProperty<SearchViewQuery>
    get() {
        val getter = { SearchViewQuery(query, false) }
        val setter: (SearchViewQuery) -> Unit = { setQuery(it.text, it.submit) }

        return createMainThreadMutableProperty(getter, setter)
    }

val SearchView.rx_queryRequirementEnabled: MutableProperty<Boolean>
    get() {
        val getter = { isQueryRefinementEnabled }
        val setter: (Boolean) -> Unit = { isQueryRefinementEnabled = it }

        return createMainThreadMutableProperty(getter, setter)
    }

val SearchView.rx_submitButtonEnabled: MutableProperty<Boolean>
    get() {
        val getter = { isSubmitButtonEnabled }
        val setter: (Boolean) -> Unit = { isSubmitButtonEnabled = it }

        return createMainThreadMutableProperty(getter, setter)
    }

val SearchView.rx_suggestionsAdapter: MutableProperty<CursorAdapter>
    get() {
        val getter = { suggestionsAdapter }
        val setter: (CursorAdapter) -> Unit = { suggestionsAdapter = it }

        return createMainThreadMutableProperty(getter, setter)
    }
