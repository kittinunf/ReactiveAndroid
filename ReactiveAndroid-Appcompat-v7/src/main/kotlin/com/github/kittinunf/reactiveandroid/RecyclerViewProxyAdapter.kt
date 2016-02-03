package com.github.kittinunf.reactiveandroid

import android.support.v7.widget.RecyclerView
import android.view.ViewGroup

/**
 * Created by Kittinun Vantasin on 2/3/16.
 */

class RecyclerViewProxyAdapter<T : RecyclerView.ViewHolder, U>(val onCreateViewHolder: (ViewGroup?, Int) -> T,
                                                            val onBindViewHolder: (T, Int, U) -> Unit,
                                                            var items: List<U>) : RecyclerView.Adapter<T>() {

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int) = onCreateViewHolder.invoke(parent, viewType)

    override fun onBindViewHolder(holder: T, position: Int) {
        onBindViewHolder.invoke(holder, position, items[position])
    }

    override fun getItemCount() = items.size

    fun updateData(recyclerView: RecyclerView, observedItems: List<U>) {
        items = observedItems
        recyclerView.post {
            notifyDataSetChanged()
        }
    }

}