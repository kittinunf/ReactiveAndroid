package com.github.kittinunf.reactiveandroid.support.v7.widget

import android.support.v7.widget.ActionMenuView
import android.view.MenuItem
import com.github.kittinunf.reactiveandroid.subscription.AndroidMainThreadSubscription
import io.reactivex.Observable

//================================================================================
// Events
//================================================================================

fun ActionMenuView.rx_menuItemClick(consumed: Boolean): Observable<MenuItem> {
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
