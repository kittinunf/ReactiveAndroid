package com.github.kittinunf.reactiveandroid.sample

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.github.kittinunf.reactiveandroid.support.v7.widget.RecyclerViewAdapterItem
import com.github.kittinunf.reactiveandroid.support.v7.widget.rx_itemsWith
import kotlinx.android.synthetic.main.activity_recyclerview.*
import kotlinx.android.synthetic.main.recycler_item.view.*
import rx.Observable
import java.util.*
import java.util.concurrent.TimeUnit

/**
 * Created by Kittinun Vantasin on 2/10/16.
 */

class RecyclerViewActivity : AppCompatActivity() {

    val date = Date()
    val calendar = Calendar.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recyclerview)

        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager = LinearLayoutManager(this)

        val o = Observable.interval(3, TimeUnit.SECONDS).scan(mutableListOf<Date>()) { acc, current ->
            calendar.time = date
            calendar.add(Calendar.MINUTE, current.toInt())
            acc.add(calendar.time)
            acc
        }

        recyclerView.rx_itemsWith(o as Observable<List<Date>>, RecyclerViewAdapter())
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val textView1: TextView by lazy(LazyThreadSafetyMode.NONE) { view.recyclerItemTextView1 }
        val textView2: TextView by lazy(LazyThreadSafetyMode.NONE) { view.recyclerItemTextView2 }
    }

    inner class RecyclerViewAdapter : RecyclerViewAdapterItem<Date, ViewHolder>() {

        override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ViewHolder? {
            val view = LayoutInflater.from(this@RecyclerViewActivity).inflate(R.layout.recycler_item, parent, false)
            return ViewHolder(view)
        }

        override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
            val date = this[position]
            viewHolder.textView1.text = date.toString()
            viewHolder.textView2.text = "Position $position"
        }

    }
}