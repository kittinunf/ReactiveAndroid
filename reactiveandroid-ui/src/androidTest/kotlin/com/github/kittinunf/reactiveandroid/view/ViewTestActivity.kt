package com.github.kittinunf.reactiveandroid.view

import android.app.Activity
import android.os.Bundle
import android.view.View
import android.widget.LinearLayout

class ViewTestActivity : Activity() {

    lateinit var parent: LinearLayout
    lateinit var child: View

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        parent = LinearLayout(this)
        child = View(this)

        setContentView(parent)
    }

}
