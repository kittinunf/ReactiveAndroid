package com.github.kittinunf.reactiveandroid.widget

import android.app.Activity
import android.os.Bundle
import android.view.ViewGroup
import android.view.ViewGroup.LayoutParams.MATCH_PARENT
import android.view.ViewGroup.LayoutParams.WRAP_CONTENT
import android.widget.LinearLayout
import android.widget.SeekBar

class SeekBarTestActivity : Activity() {

    lateinit var seekBar: SeekBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        seekBar = SeekBar(this).apply {
            max = 100
            progress = 0
            layoutParams = ViewGroup.LayoutParams(MATCH_PARENT, WRAP_CONTENT)
        }

        val parent = LinearLayout(this).apply {
            addView(seekBar)
        }

        setContentView(parent)
    }

}