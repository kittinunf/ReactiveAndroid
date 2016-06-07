package com.github.kittinunf.reactiveandroid.sample.view

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.Menu
import android.view.View
import android.widget.TextView
import com.github.kittinunf.reactiveandroid.MutableProperty
import com.github.kittinunf.reactiveandroid.rx.addTo
import com.github.kittinunf.reactiveandroid.sample.R
import com.github.kittinunf.reactiveandroid.support.v7.widget.rx_itemsWith
import com.github.kittinunf.reactiveandroid.view.rx_click
import com.github.kittinunf.reactiveandroid.view.rx_longClick
import com.github.kittinunf.reactiveandroid.view.rx_menuItemClick
import kotlinx.android.synthetic.main.activity_recycler_view.*
import kotlinx.android.synthetic.main.recycler_item.view.*
import rx.subscriptions.CompositeSubscription
import java.util.*

class RecyclerViewActivity : AppCompatActivity() {

    val compositeSubscription = CompositeSubscription()

    //back storage
    val _items = mutableListOf<String>()

    val items = MutableProperty<List<String>>()

    lateinit var countries: Array<String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recycler_view)
        setSupportActionBar(toolbar)

        countries = resources.getStringArray(R.array.countries)

        titleTextView.text = javaClass.simpleName

        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager = LinearLayoutManager(this)

        recyclerView.rx_itemsWith(items.observable, { parent, viewType ->
            val view = LayoutInflater.from(parent?.context).inflate(R.layout.recycler_item, parent, false)
            val vh = ViewHolder(view)
            vh.itemView.rx_click().subscribe {
                _items[vh.layoutPosition] = countries.random()
                items.value = _items.toList()
            }
            vh.itemView.rx_longClick(true).subscribe {
                _items.removeAt(vh.layoutPosition)
                items.value = _items.toList()
            }
            vh
        }, { holder, position, item ->
            holder.textView1.text = item
        }).addTo(compositeSubscription)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menu?.let {
            menuInflater.inflate(R.menu.menus, menu)
            menu.findItem(R.id.menu_add).rx_menuItemClick(true).subscribe {
                _items.add(_items.size.random(), countries.random())
                items.value = _items.toList()
            }.addTo(compositeSubscription)
        }
        return true
    }

    override fun onDestroy() {
        super.onDestroy()
        compositeSubscription.unsubscribe()
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val textView1: TextView by lazy(LazyThreadSafetyMode.NONE) { view.recyclerItemTextView1 }
        val textView2: TextView by lazy(LazyThreadSafetyMode.NONE) { view.recyclerItemTextView2 }
    }

    fun Int.random() = Random().nextInt(this + 1)

    fun <T> Array<T>.random() = this[this.size.random()]

}
