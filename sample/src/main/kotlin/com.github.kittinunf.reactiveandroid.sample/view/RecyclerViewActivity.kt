package com.github.kittinunf.reactiveandroid.sample.view

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.widget.TextView
import com.github.kittinunf.reactiveandroid.rx.addTo
import com.github.kittinunf.reactiveandroid.sample.R
import com.github.kittinunf.reactiveandroid.support.v7.widget.SECTION_HEADER_VIEW_TYPE
import com.github.kittinunf.reactiveandroid.support.v7.widget.SectionModelType
import com.github.kittinunf.reactiveandroid.support.v7.widget.rx_itemsWith
import kotlinx.android.synthetic.main.activity_recyclerview.*
import kotlinx.android.synthetic.main.recycler_header_item.view.*
import kotlinx.android.synthetic.main.recycler_item.view.*
import rx.Observable
import rx.subscriptions.CompositeSubscription

class RecyclerViewActivity : AppCompatActivity() {

    val compositeSubscription = CompositeSubscription()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recyclerview)

        titleTextView.text = javaClass.simpleName

        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager = LinearLayoutManager(this)

        val rawCountries = resources.getStringArray(R.array.countries)
        val rawCapitals = resources.getStringArray(R.array.capitals)

        val items = rawCountries.mapIndexed { index, country ->
            val capital = rawCapitals[index]
            Item(country, capital)
        }

        val itemMap = items.groupBy { it.country.first() }.map { Section(it.key, it.value) }

        val o = Observable.just(itemMap)
        recyclerView.rx_itemsWith(o, { parent, viewType ->
            val resId = if (viewType == SECTION_HEADER_VIEW_TYPE) R.layout.recycler_header_item else R.layout.recycler_item
            val view = LayoutInflater.from(parent?.context).inflate(resId, parent, false)
            ViewHolder(view)
        }, { holder, position, section ->
            holder.headerTextView.text = section.name.toString()
        }, { holder, position, item ->
            holder.textView1.text = item.country
            holder.textView2.text = item.capital
        }).addTo(compositeSubscription)
    }

    override fun onDestroy() {
        super.onDestroy()
        compositeSubscription.unsubscribe()
    }

    class Section(val name: Char, override var items: List<Item>) : SectionModelType<Item>

    class Item(val country: String, val capital: String)

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val headerTextView: TextView by lazy(LazyThreadSafetyMode.NONE) { view.recyclerHeaderTextView }

        val textView1: TextView by lazy(LazyThreadSafetyMode.NONE) { view.recyclerItemTextView1 }
        val textView2: TextView by lazy(LazyThreadSafetyMode.NONE) { view.recyclerItemTextView2 }
    }

}