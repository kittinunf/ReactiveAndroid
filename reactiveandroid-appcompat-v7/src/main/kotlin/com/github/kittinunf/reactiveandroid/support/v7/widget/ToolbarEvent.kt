package com.github.kittinunf.reactiveandroid.support.v7.widget

import android.support.v7.widget.Toolbar
import android.view.MenuItem
import android.view.View
import com.github.kittinunf.reactiveandroid.subscription.AndroidMainThreadSubscription
import io.reactivex.Observable

//================================================================================
// Events
//================================================================================

fun Toolbar.rx_navigationClick(): Observable<View> {
    return Observable.create { subscriber ->
        setNavigationOnClickListener {
            subscriber.onNext(it)

        }

        subscriber.setDisposable(AndroidMainThreadSubscription {
            setNavigationOnClickListener(null)
        })
    }
}

fun Toolbar.rx_menuItemClick(consumed: Boolean): Observable<MenuItem> {
    return Observable.create { subscriber ->
        setOnMenuItemClickListener {
            subscriber.onNext(it)
            consumed
        }

        subscriber.setDisposable(AndroidMainThreadSubscription {
            setOnMenuItemClickListener(null)
        })
    }
}
