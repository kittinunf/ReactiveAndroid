package com.github.kittinunf.reactiveandroid.widget

import android.support.v7.widget.RecyclerView
import android.view.ViewGroup
import com.github.kittinunf.reactiveandroid.RecyclerViewProxyAdapter
import rx.Observable
import rx.Subscription

/**
 * Created by Kittinun Vantasin on 2/3/16.
 */

fun <T : RecyclerView.ViewHolder, U> RecyclerView.rx_itemsWith(observable: Observable<List<U>>,
                                                               onCreateViewHolder: (ViewGroup?, Int) -> T,
                                                               onBindViewHolder: (T, Int, U) -> Unit) : Subscription {

    val proxyAdapter = RecyclerViewProxyAdapter(onCreateViewHolder, onBindViewHolder, emptyList<U>())
    adapter = proxyAdapter
    return observable.subscribe {
        proxyAdapter.updateData(this, it)
    }
}