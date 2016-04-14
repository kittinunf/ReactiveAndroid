package com.github.kittinunf.reactiveandroid.widget

import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.BaseAdapter
import rx.Observable
import rx.Subscription

abstract class AdapterViewProxyAdapter<T> : BaseAdapter() {

    internal var items: List<T> = listOf()

    override fun getCount(): Int = items.size

    override fun getItem(position: Int) = items[position]

    operator fun get(position: Int) = items[position]
}

fun <T> AdapterView<*>.rx_itemsWith(observable: Observable<List<T>>,
                                    getView: (Int, T, View?, ViewGroup?) -> View,
                                    getItemId: ((Int, T) -> Long)? = null): Subscription {

    val proxyAdapter = object : AdapterViewProxyAdapter<T>() {

        override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View? = getView.invoke(position, items[position], convertView, parent)

        override fun getItemId(position: Int): Long = getItemId?.invoke(position, items[position]) ?: position.toLong()

    }
    return rx_itemsWith(observable, proxyAdapter)
}

fun <T, U : AdapterViewProxyAdapter<T>> AdapterView<*>.rx_itemsWith(observable: Observable<List<T>>,
                                              adapterViewProxyAdapter: U): Subscription {
    adapter = adapterViewProxyAdapter
    return observable.subscribe {
        adapterViewProxyAdapter.items = it
        post {
            adapterViewProxyAdapter.notifyDataSetChanged()
        }
    }
}
