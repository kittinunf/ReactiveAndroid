package com.github.kittinunf.reactiveandroid.widget

import android.app.Activity
import android.os.Bundle
import android.widget.LinearLayout
import android.widget.RatingBar

class RatingBarTestActivity : Activity() {

    lateinit var ratingBar: RatingBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        ratingBar = RatingBar(this).apply {
            id = android.R.id.input
            max = 5
            stepSize = 1.0f
        }

        val parent = LinearLayout(this)
        parent.addView(ratingBar)
        setContentView(parent)
    }

}