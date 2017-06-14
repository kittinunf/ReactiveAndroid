package com.github.kittinunf.reactiveandroid.widget

import android.app.Activity
import android.os.Bundle
import android.widget.LinearLayout
import android.widget.TextView

class TextViewTestActivity : Activity() {

    lateinit var textView: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        textView = TextView(this)

        val parent = LinearLayout(this).apply {
            addView(textView)
        }

        setContentView(parent)
    }

}