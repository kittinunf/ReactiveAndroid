package com.github.kittinunf.reactiveandroid.support.v7.widget

import android.support.v7.widget.RecyclerView
import android.view.ViewGroup
import rx.Observable
import rx.Subscription

abstract class RecyclerViewProxyAdapter<T, VH : RecyclerView.ViewHolder> : RecyclerView.Adapter<VH>() {

    internal var items: List<T> = listOf()

    abstract var createViewHolder: (ViewGroup?, Int) -> VH
    abstract var bindViewHolder: (VH, Int, T) -> Unit

    override fun getItemCount(): Int = items.size

    fun getItem(position: Int) = items[position]
    operator fun get(position: Int) = items[position]

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): VH = createViewHolder.invoke(parent, viewType)

    override fun onBindViewHolder(viewHolder: VH, position: Int) {
        bindViewHolder.invoke(viewHolder, position, items[position])
    }

}

fun <VH : RecyclerView.ViewHolder, T, C: List<T>> RecyclerView.rx_itemsWith(observable: Observable<C>,
                                                               onCreateViewHolder: (ViewGroup?, Int) -> VH,
                                                               onBindViewHolder: (VH, Int, T) -> Unit): Subscription {
    val proxyAdapter = object : RecyclerViewProxyAdapter<T, VH>() {
        override var createViewHolder: (ViewGroup?, Int) -> VH = onCreateViewHolder

        override var bindViewHolder: (VH, Int, T) -> Unit = onBindViewHolder
    }
    return rx_itemsWith(observable, proxyAdapter)
}

fun <VH : RecyclerView.ViewHolder, T, C: List<T>, A : RecyclerViewProxyAdapter<T, VH>> RecyclerView.rx_itemsWith(observable: Observable<C>,
                                                                                                   recyclerProxyAdapter: A): Subscription {
    adapter = recyclerProxyAdapter
    return observable.subscribe {
        recyclerProxyAdapter.items = it
        post { recyclerProxyAdapter.notifyDataSetChanged() }
    }
}