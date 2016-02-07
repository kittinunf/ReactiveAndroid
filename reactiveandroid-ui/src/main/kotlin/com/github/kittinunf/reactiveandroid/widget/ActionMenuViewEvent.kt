package com.github.kittinunf.reactiveandroid.widget

import android.view.MenuItem
import android.widget.ActionMenuView
import com.github.kittinunf.reactiveandroid.subscription.AndroidMainThreadSubscription
import rx.Observable

//================================================================================
// Events
//================================================================================

fun ActionMenuView.rx_menuItemClick(consumed: Boolean): Observable<MenuItem> {
    return Observable.create { subscriber ->
        setOnMenuItemClickListener {
            subscriber.onNext(it)
            consumed
        }

        subscriber.add(AndroidMainThreadSubscription {
            setOnMenuItemClickListener(null)
        })
    }
}
 
