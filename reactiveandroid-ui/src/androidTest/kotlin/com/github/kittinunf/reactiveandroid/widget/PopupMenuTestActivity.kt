package com.github.kittinunf.reactiveandroid.widget

import android.app.Activity
import android.os.Bundle
import android.widget.Button
import android.widget.LinearLayout
import android.widget.PopupMenu

class PopupMenuTestActivity : Activity() {

    lateinit var popupMenu: PopupMenu

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val anchored = Button(this)
        popupMenu = PopupMenu(this, anchored)

        val linearLayout = LinearLayout(this).apply {
            addView(anchored)
        }

        setContentView(linearLayout)
    }

}
