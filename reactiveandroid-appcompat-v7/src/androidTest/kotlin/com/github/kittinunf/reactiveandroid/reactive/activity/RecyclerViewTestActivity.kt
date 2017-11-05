package com.github.kittinunf.reactiveandroid.reactive.activity

import android.app.Activity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView

class RecyclerViewTestActivity : Activity() {

    lateinit var recyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        recyclerView = RecyclerView(this).apply {
            id = android.R.id.primary
            layoutManager = LinearLayoutManager(this@RecyclerViewTestActivity)
        }

        setContentView(recyclerView)
    }
}