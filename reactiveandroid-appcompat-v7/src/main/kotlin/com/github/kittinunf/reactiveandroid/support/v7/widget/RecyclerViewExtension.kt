package com.github.kittinunf.reactiveandroid.support.v7.widget

import android.support.v7.widget.RecyclerView
import android.view.ViewGroup
import rx.Observable
import rx.Subscription

fun <T : RecyclerView.ViewHolder, U> RecyclerView.rx_itemsWith(observable: Observable<List<U>>,
                                                               onCreateViewHolder: (ViewGroup?, Int) -> T,
                                                               onBindViewHolder: (T, Int, U) -> Unit): Subscription {

    val proxyAdapter = RecyclerViewProxyAdapter(onCreateViewHolder, onBindViewHolder, emptyList<U>())
    adapter = proxyAdapter
    return observable.subscribe {
        proxyAdapter.updateData(this, it)
    }
}

abstract class RecyclerViewAdapterItem<T, VH : RecyclerView.ViewHolder> : RecyclerView.Adapter<VH>() {
    internal var items: List<T> = listOf()

    override fun getItemCount(): Int = items.size

    fun getItem(position: Int) = items[position]
    operator fun get(position: Int) = items[position]
}

fun <T : RecyclerView.ViewHolder, U, V : RecyclerViewAdapterItem<U, T>> RecyclerView.rx_itemsWith(observable: Observable<List<U>>,
                                                               recyclerViewAdapter: V): Subscription {
    adapter = recyclerViewAdapter
    return observable.subscribe {
        recyclerViewAdapter.items = it
        post {
            recyclerViewAdapter.notifyDataSetChanged()
        }
    }
}