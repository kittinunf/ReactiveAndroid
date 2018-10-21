package com.github.kittinunf.reactiveandroidx.appcompat.widget

import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.github.kittinunf.reactiveandroid.subscription.AndroidMainThreadSubscription
import io.reactivex.Observable

//================================================================================
// Event
//================================================================================

fun SwipeRefreshLayout.rx_refresh(): Observable<Unit> {
    return Observable.create { subscriber ->
        setOnRefreshListener { ->
            subscriber.onNext(Unit)
        }

        subscriber.setDisposable(AndroidMainThreadSubscription {
            setOnRefreshListener(null)
        })
    }
}
