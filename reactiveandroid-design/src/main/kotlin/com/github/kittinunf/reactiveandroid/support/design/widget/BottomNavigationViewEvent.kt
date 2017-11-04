package com.github.kittinunf.reactiveandroid.support.design.widget

import android.support.design.widget.BottomNavigationView
import android.view.MenuItem
import com.github.kittinunf.reactiveandroid.subscription.AndroidMainThreadSubscription
import io.reactivex.Observable

//================================================================================
// Events
//================================================================================

fun BottomNavigationView.rx_navigationItemSelected(selected: Boolean): Observable<MenuItem> {
    return Observable.create { subscriber ->
        setOnNavigationItemSelectedListener {
            subscriber.onNext(it)
            selected
        }

        subscriber.setDisposable(AndroidMainThreadSubscription {
            setOnNavigationItemSelectedListener(null)
        })
    }
}
