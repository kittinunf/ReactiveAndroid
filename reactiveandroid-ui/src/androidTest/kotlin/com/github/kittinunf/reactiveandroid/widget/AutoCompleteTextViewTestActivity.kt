package com.github.kittinunf.reactiveandroid.widget

import android.app.Activity
import android.os.Bundle
import android.view.ViewGroup
import android.view.ViewGroup.LayoutParams.MATCH_PARENT
import android.view.ViewGroup.LayoutParams.WRAP_CONTENT
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.LinearLayout

class AutoCompleteTextViewTestActivity : Activity() {

    lateinit var textView: AutoCompleteTextView

    private lateinit var items: List<String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        textView = AutoCompleteTextView(this)
        textView.layoutParams = ViewGroup.LayoutParams(MATCH_PARENT, WRAP_CONTENT)
        items = listOf("two", "three", "four", "twenty one", "thirty two", "forty three")
        textView.setAdapter(ArrayAdapter(this, android.R.layout.simple_list_item_1, items))
        textView.id = android.R.id.input

        val parent = LinearLayout(this)
        parent.addView(textView)
        setContentView(parent)
    }

}
