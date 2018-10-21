package com.github.kittinunf.reactiveandroidx.appcompat.widget

import android.view.MenuItem
import androidx.appcompat.widget.ActionMenuView
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
