package com.github.kittinunf.reactiveandroid.reactive.activity

import android.app.Activity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup

class RecyclerViewTestActivity : Activity() {

    lateinit var recyclerView: RecyclerView
    lateinit var child: View

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        child = View(this)

        recyclerView = RecyclerView(this).apply {
            id = android.R.id.primary
            layoutManager = LinearLayoutManager(this@RecyclerViewTestActivity)

        }

        setContentView(recyclerView)
    }

    fun setItem1Adapter(view: View) {
        runOnUiThread {
            recyclerView.adapter = Item1Adapter(view)
        }
    }

    fun unsetAdapter() {
        runOnUiThread {
            recyclerView.adapter = null
        }
    }

    class Item1Adapter(val child: View) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

        override fun getItemCount(): Int = 1

        override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): RecyclerView.ViewHolder = object : RecyclerView.ViewHolder(child) {
        }

        override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
            println(position)
        }
    }
}