package com.github.kittinunf.reactiveandroid.support.v7.widget

import android.support.v7.widget.RecyclerView
import android.view.ViewGroup
import com.github.kittinunf.reactiveandroid.rx.cachedPrevious
import com.github.kittinunf.reactiveandroid.scheduler.AndroidThreadScheduler
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
    return observable.cachedPrevious().observeOn(AndroidThreadScheduler.main).subscribe {
        val (previous, current) = it
        recyclerProxyAdapter.items = current!!

        //calculate diff
        val diffPatch = DiffUtils.diff(previous ?: listOf(), current)
        if (diffPatch.deltas.size > 20) {
            recyclerProxyAdapter.notifyDataSetChanged()
        } else if (diffPatch.deltas.size > 0) {
            for (d in diffPatch.deltas) {
                val original = d.original
                val revised = d.revised
                when (d.type) {
                    Delta.TYPE.DELETE -> {
                        recyclerProxyAdapter.notifyItemRangeRemoved(revised.position, original.size())
                    }
                    Delta.TYPE.CHANGE -> {
                        recyclerProxyAdapter.notifyItemRangeChanged(revised.position, revised.size())
                    }
                    Delta.TYPE.INSERT -> {
                        recyclerProxyAdapter.notifyItemRangeInserted(revised.position, revised.size())
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