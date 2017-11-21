package com.github.kittinunf.reactiveandroid.sample.view

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.TextView
import com.github.kittinunf.reactiveandroid.sample.R
import kotlinx.android.synthetic.main.activity_nested_recycler_view.firstRecyclerView
import kotlinx.android.synthetic.main.activity_nested_recycler_view.secondRecyclerView

class NestedRecyclerViewActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_nested_recycler_view)

        firstRecyclerView.apply {
            layoutManager = LinearLayoutManager(this@NestedRecyclerViewActivity)
//            rx_itemsWith(Observable.just(listOf(1, 2, 3, 4, 5, 6, 7, 8)), { parent, viewType ->
//                val v = LayoutInflater.from(parent?.context).inflate(android.R.layout.simple_list_item_1, parent, false)
//                ViewHolder(v)
//            }, { holder, position, item ->
//                holder.textView.text = item.toString()
//            })
        }

        secondRecyclerView.apply {
            layoutManager = LinearLayoutManager(this@NestedRecyclerViewActivity)
//            rx_itemsWith(Observable.just(listOf(1, 2, 3, 4, 5, 6, 7, 8)), { parent, viewType ->
//                val v = LayoutInflater.from(parent?.context).inflate(android.R.layout.simple_list_item_1, parent, false)
//                ViewHolder(v)
//            }, { holder, position, item ->
//                holder.textView.text = item.toString()
//            })
        }

    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val textView by lazy { view.findViewById<TextView>(android.R.id.text1) }
    }

}
