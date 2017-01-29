package com.github.kittinunf.reactiveandroid.widget

import android.app.Activity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.LinearLayout
import android.widget.ListView
import android.widget.Spinner

class AdapterViewTestActivity : Activity() {

    lateinit var spinner: Spinner
    lateinit var listView: ListView

    private lateinit var items: MutableList<String>
    private lateinit var adapter: ArrayAdapter<String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        spinner = Spinner(this)
        listView = ListView(this)

        items = mutableListOf("1", "2", "3", "4", "5")
        adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, items)

        spinner.adapter = adapter
        listView.adapter = adapter

        val parent = LinearLayout(this)
        parent.addView(spinner)
        parent.addView(listView)

        setContentView(parent)
    }

    fun clear() {
        items.clear()
        adapter.notifyDataSetChanged()
    }

}