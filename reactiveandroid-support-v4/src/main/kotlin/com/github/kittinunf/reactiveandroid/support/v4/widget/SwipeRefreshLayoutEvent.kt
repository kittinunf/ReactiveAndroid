package com.github.kittinunf.reactiveandroid.support.v4.widget

import android.support.v4.widget.SwipeRefreshLayout
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
