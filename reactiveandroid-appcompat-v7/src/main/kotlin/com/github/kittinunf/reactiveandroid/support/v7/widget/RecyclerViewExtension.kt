package com.github.kittinunf.reactiveandroid.support.v7.widget

import android.support.v7.widget.RecyclerView
import android.view.ViewGroup
import rx.Observable
import rx.Subscription

fun <T : RecyclerView.ViewHolder, U> RecyclerView.rx_itemsWith(observable: Observable<List<U>>,
                                                               onCreateViewHolder: (ViewGroup?, Int) -> T,
                                                               onBindViewHolder: (T, Int) -> Unit): Subscription {
    val proxy = RecyclerViewImpl<U, T>(onCreateViewHolder, onBindViewHolder)
    return rx_itemsWith(observable, proxy)
}

class RecyclerViewImpl<T, VH: RecyclerView.ViewHolder>(override var createViewHolder: (ViewGroup?, Int) -> VH,
                                                       override var bindViewHolder: (VH, Int) -> Unit) : RecyclerViewProxyAdapter<T, VH>()

abstract class RecyclerViewProxyAdapter<T, VH : RecyclerView.ViewHolder> : RecyclerView.Adapter<VH>() {

    internal var items: List<T> = listOf()

    abstract var createViewHolder: (ViewGroup?, Int) -> VH
    abstract var bindViewHolder: (VH, Int) -> Unit

    override fun getItemCount(): Int = items.size

    fun getItem(position: Int) = items[position]
    operator fun get(position: Int) = items[position]

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): VH = createViewHolder.invoke(parent, viewType)

    override fun onBindViewHolder(viewHolder: VH, position: Int) {
        bindViewHolder.invoke(viewHolder, position)
    }

}

fun <T : RecyclerView.ViewHolder, U, V : RecyclerViewProxyAdapter<U, T>> RecyclerView.rx_itemsWith(observable: Observable<List<U>>,
                                                               recyclerProxyAdapter: V): Subscription {
    adapter = recyclerProxyAdapter
    return observable.subscribe {
        recyclerProxyAdapter.items = it
        post {
            recyclerProxyAdapter.notifyDataSetChanged()
        }
    }
}