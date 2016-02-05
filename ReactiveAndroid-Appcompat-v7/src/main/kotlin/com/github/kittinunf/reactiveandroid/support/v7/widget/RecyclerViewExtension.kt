package com.github.kittinunf.reactiveandroid.support.v7.widget

import android.support.v7.widget.RecyclerView
import android.view.ViewGroup
import com.github.kittinunf.reactiveandroid.support.v7.widget.RecyclerViewProxyAdapter
import rx.Observable
import rx.Subscription

fun <T : RecyclerView.ViewHolder, U> RecyclerView.rx_itemsWith(observable: Observable<List<U>>,
                                                               onCreateViewHolder: (ViewGroup?, Int) -> T,
                                                               onBindViewHolder: (T, Int, U) -> Unit) : Subscription {

    val proxyAdapter = RecyclerViewProxyAdapter(onCreateViewHolder, onBindViewHolder, emptyList<U>())
    adapter = proxyAdapter
    return observable.subscribe {
        proxyAdapter.updateData(this, it)
    }
}