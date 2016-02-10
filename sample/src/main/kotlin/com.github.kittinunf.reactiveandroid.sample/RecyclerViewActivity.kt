package com.github.kittinunf.reactiveandroid.sample

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.github.kittinunf.reactiveandroid.rx.addTo
import com.github.kittinunf.reactiveandroid.support.v7.widget.RecyclerViewProxyAdapter
import com.github.kittinunf.reactiveandroid.support.v7.widget.rx_itemsWith
import kotlinx.android.synthetic.main.activity_recyclerview.*
import kotlinx.android.synthetic.main.recycler_item.view.*
import rx.Observable
import rx.schedulers.Schedulers
import rx.subscriptions.CompositeSubscription
import java.util.*
import java.util.concurrent.TimeUnit

class RecyclerViewActivity : AppCompatActivity() {

    val date = Date()
    val calendar = Calendar.getInstance()

    val compositeSubscription = CompositeSubscription()

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
        }.subscribeOn(Schedulers.newThread())

        recyclerView.rx_itemsWith(o as Observable<List<Date>>, RecyclerViewAdapter()).addTo(compositeSubscription)
    }

    override fun onDestroy() {
        super.onDestroy()
        compositeSubscription.unsubscribe()
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val textView1: TextView by lazy(LazyThreadSafetyMode.NONE) { view.recyclerItemTextView1 }
        val textView2: TextView by lazy(LazyThreadSafetyMode.NONE) { view.recyclerItemTextView2 }
    }

    inner class RecyclerViewAdapter : RecyclerViewProxyAdapter<Date, ViewHolder>() {
        override var createViewHolder: (ViewGroup?, Int) -> ViewHolder = { parent, viewType ->
            val view = LayoutInflater.from(this@RecyclerViewActivity).inflate(R.layout.recycler_item, parent, false)
            ViewHolder(view)
        }
        override var bindViewHolder: (ViewHolder, Int, Date) -> Unit = { viewHolder, position, date ->
            viewHolder.textView1.text = date.toString()
            viewHolder.textView2.text = "Position $position"
        }


    }
}