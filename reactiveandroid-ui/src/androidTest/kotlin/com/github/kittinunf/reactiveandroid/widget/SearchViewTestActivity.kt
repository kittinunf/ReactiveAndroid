package com.github.kittinunf.reactiveandroid.widget

import android.app.Activity
import android.os.Bundle
import android.widget.LinearLayout
import android.widget.SearchView
import android.widget.SimpleCursorAdapter

class SearchViewTestActivity : Activity() {

    lateinit var searchView: SearchView

    lateinit var adapter: SimpleCursorAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        searchView = SearchView(this).apply {
            setIconifiedByDefault(true)
        }

        val parent = LinearLayout(this).apply {
            addView(searchView)
        }

        setContentView(parent)
    }

}
