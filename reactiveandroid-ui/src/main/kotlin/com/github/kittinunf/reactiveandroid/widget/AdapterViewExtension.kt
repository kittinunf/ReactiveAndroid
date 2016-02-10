package com.github.kittinunf.reactiveandroid.widget

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

fun <T, U : AdapterViewProxyAdapter<T>> AdapterView<U>.rx_itemsWith(observable: Observable<List<T>>,
                                              adapterViewProxyAdapter: U): Subscription {
    adapter = adapterViewProxyAdapter
    return observable.subscribe {
        adapterViewProxyAdapter.items = it
        post {
            adapterViewProxyAdapter.notifyDataSetChanged()
        }
    }
}
