package com.github.kittinunf.reactiveandroidx.appcompat.reactive.widget

import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.github.kittinunf.reactiveandroid.reactive.Reactive
import com.github.kittinunf.reactiveandroid.reactive.cachedPrevious
import com.github.kittinunf.reactiveandroid.scheduler.AndroidThreadScheduler
import io.reactivex.Observable
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

abstract class RecyclerViewProxyAdapter<T, VH : RecyclerView.ViewHolder> : RecyclerView.Adapter<VH>() {

    internal var items: List<T> = listOf()

    abstract var createViewHolder: (ViewGroup?, Int) -> VH
    abstract var bindViewHolder: (VH, Int, T) -> Unit
    open var itemViewType: ((Int) -> Int)? = null

    override fun getItemCount(): Int = items.size

    fun getItem(position: Int) = items[position]
    operator fun get(position: Int) = items[position]

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH = createViewHolder.invoke(parent, viewType)

    override fun onBindViewHolder(viewHolder: VH, position: Int) {
        bindViewHolder.invoke(viewHolder, position, items[position])
    }

    override fun getItemViewType(position: Int): Int =
            itemViewType?.invoke(position) ?: super.getItemViewType(position)

}

fun <T, VH : RecyclerView.ViewHolder> Reactive<RecyclerView>.bind(items: Observable<List<T>>,
                                                                  onCreateViewHolder: (ViewGroup?, Int) -> VH,
                                                                  onBindViewHolder: (VH, Int, T) -> Unit,
                                                                  onItemViewType: ((Int) -> Int)? = null): Disposable {
    val proxy = object : RecyclerViewProxyAdapter<T, VH>() {
        override var createViewHolder: (ViewGroup?, Int) -> VH = onCreateViewHolder
        override var bindViewHolder: (VH, Int, T) -> Unit = onBindViewHolder
        override var itemViewType: ((Int) -> Int)? = onItemViewType
    }
    return bind(items, proxy)
}

fun <T, VH : RecyclerView.ViewHolder> Reactive<RecyclerView>.bind(items: Observable<List<T>>,
                                                                  proxy: RecyclerViewProxyAdapter<T, VH>): Disposable {

    class DefaultDiffCallBack(val oldItems: List<T>, val newItems: List<T>) : DiffUtil.Callback() {
        override fun getOldListSize(): Int = oldItems.size

        override fun getNewListSize(): Int = newItems.size

        override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean =
                oldItems[oldItemPosition] == newItems[newItemPosition]

        override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean =
                oldItems[oldItemPosition] == newItems[newItemPosition]

    }

    return bind(items, proxy) { oldItems, newItems -> DefaultDiffCallBack(oldItems, newItems) }
}

fun <T, VH : RecyclerView.ViewHolder> Reactive<RecyclerView>.bind(items: Observable<List<T>>,
                                                                  proxy: RecyclerViewProxyAdapter<T, VH>,
                                                                  callback: (List<T>, List<T>) -> DiffUtil.Callback): Disposable {
    item.adapter = proxy

    return items.cachedPrevious()
            .map { (oldItems, newItems) ->
                proxy.items = newItems ?: emptyList()
                DiffUtil.calculateDiff(callback(oldItems ?: emptyList(), newItems ?: emptyList()))
            }
            .subscribeOn(Schedulers.computation())
            .observeOn(AndroidThreadScheduler.main)
            .subscribe { result -> result.dispatchUpdatesTo(proxy) }
}
