package com.github.kittinunf.reactiveandroid.support.v7.widget

import android.support.v7.widget.RecyclerView
import android.view.ViewGroup
import com.github.kittinunf.reactiveandroid.rx.cachedPrevious
import difflib.Delta
import difflib.DiffUtils
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

fun <VH : RecyclerView.ViewHolder, T, C : List<T>> RecyclerView.rx_itemsWith(observable: Observable<C>,
                                                                             onCreateViewHolder: (ViewGroup?, Int) -> VH,
                                                                             onBindViewHolder: (VH, Int, T) -> Unit): Subscription {
    val proxyAdapter = object : RecyclerViewProxyAdapter<T, VH>() {
        override var createViewHolder: (ViewGroup?, Int) -> VH = onCreateViewHolder

        override var bindViewHolder: (VH, Int, T) -> Unit = onBindViewHolder
    }
    return rx_itemsWith(observable, proxyAdapter)
}

fun <VH : RecyclerView.ViewHolder, T, C : List<T>, A : RecyclerViewProxyAdapter<T, VH>> RecyclerView.rx_itemsWith(observable: Observable<C>,
                                                                                                                  recyclerProxyAdapter: A): Subscription {
    adapter = recyclerProxyAdapter
    return observable.cachedPrevious().subscribe {
        val (previous, current) = it
        recyclerProxyAdapter.items = current!!

        //calculate diff
        val diffPatch = DiffUtils.diff(previous ?: listOf(), current)
        if (diffPatch.deltas.size > 20) {
            post { recyclerProxyAdapter.notifyDataSetChanged() }
        } else if (diffPatch.deltas.size > 0) {
            for (d in diffPatch.deltas) {
                val original = d.original
                val revised = d.revised
                when (d.type) {
                    Delta.TYPE.DELETE -> {
                        val deletedIndices = original.lines.mapTo(mutableListOf()) { previous!!.indexOf(it) }
                        deletedIndices.forEach { recyclerProxyAdapter.notifyItemRemoved(it) }
                    }
                    Delta.TYPE.CHANGE -> {
                        val changeIndices = revised.lines.mapTo(mutableListOf()) { current.indexOf(it) }
                        changeIndices.forEach { recyclerProxyAdapter.notifyItemChanged(it) }
                    }
                    Delta.TYPE.INSERT -> {
                        //first inserting, insert range
                        val insertedIndices = revised.lines.mapTo(mutableListOf()) { current.indexOf(it) }
                        insertedIndices.forEach { recyclerProxyAdapter.notifyItemInserted(it) }
                    }
                    else -> {
                    }
                }
            }
        } else {
            // no change
        }

    }
}