package com.github.kittinunf.reactiveandroid.support.v7.reactive.activity

import android.app.Activity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import kotlin.properties.Delegates

class RecyclerViewTestActivity : Activity() {

    lateinit var recyclerView: RecyclerView
    lateinit var child: View

    var items by Delegates.observable((1..100).toList()) { _, _, _ ->
        runOnUiThread {
            recyclerView.adapter?.notifyDataSetChanged()
        }
    }

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

    fun setItemsAdapter() {
        runOnUiThread {
            recyclerView.adapter = ItemsAdapter()
        }
    }

    fun unsetAdapter() {
        runOnUiThread {
            recyclerView.adapter = null
        }
    }

    class Item1Adapter(val child: View) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

        override fun getItemCount(): Int = 1

        override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): RecyclerView.ViewHolder =
                object : RecyclerView.ViewHolder(child) {}

        override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        }
    }

    class ViewHolder(val view: TextView) : RecyclerView.ViewHolder(view)

    inner class ItemsAdapter : RecyclerView.Adapter<ViewHolder>() {

        override fun getItemCount(): Int = items.size

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            val textView = LayoutInflater.from(parent.context).inflate(android.R.layout.simple_list_item_1, parent, false)
            return ViewHolder(textView as TextView)
        }

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            holder.view.text = position.toString()
        }

        override fun getItemId(position: Int): Long = items[position].toLong()
    }
}