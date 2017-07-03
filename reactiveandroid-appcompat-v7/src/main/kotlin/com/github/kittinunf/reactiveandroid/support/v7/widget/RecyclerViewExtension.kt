package com.github.kittinunf.reactiveandroid.support.v7.widget

import android.support.v7.util.DiffUtil
import android.support.v7.widget.RecyclerView
import android.view.ViewGroup
import com.github.kittinunf.reactiveandroid.reactive.cachedPrevious
import com.github.kittinunf.reactiveandroid.scheduler.AndroidThreadScheduler
import io.reactivex.Observable
import io.reactivex.disposables.Disposable

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
                                                                             onBindViewHolder: (VH, Int, T) -> Unit): Disposable {
    val proxyAdapter = object : RecyclerViewProxyAdapter<T, VH>() {
        override var createViewHolder: (ViewGroup?, Int) -> VH = onCreateViewHolder

        override var bindViewHolder: (VH, Int, T) -> Unit = onBindViewHolder
    }
    return rx_itemsWith(observable, proxyAdapter)
}

fun <VH : RecyclerView.ViewHolder, T, C : List<T>, A : RecyclerViewProxyAdapter<T, VH>> RecyclerView.rx_itemsWith(observable: Observable<C>,
                                                                                                                  recyclerProxyAdapter: A): Disposable {
    adapter = recyclerProxyAdapter

    class DiffCallback(val oldData: List<T>, val newData: List<T>) : DiffUtil.Callback() {

        override fun getOldListSize(): Int = oldData.size

        override fun getNewListSize(): Int = newData.size

        override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean =
                oldData[oldItemPosition] == newData[newItemPosition]

        override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean =
                oldData[oldItemPosition] == newData[newItemPosition]

    }

    return observable.cachedPrevious().observeOn(AndroidThreadScheduler.main).subscribe {
        val (previous, current) = it
        recyclerProxyAdapter.items = current!!

        //calculate diff
        val diffResult = DiffUtil.calculateDiff(DiffCallback(previous ?: listOf(), current))
        diffResult.dispatchUpdatesTo(recyclerProxyAdapter)
    }
}
