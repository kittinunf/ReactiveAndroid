package com.github.kittinunf.reactiveandroid.support.design.widget

import android.support.design.widget.NavigationView
import android.view.MenuItem
import com.github.kittinunf.reactiveandroid.subscription.AndroidMainThreadSubscription
import io.reactivex.Observable

//================================================================================
// Events
//================================================================================

fun NavigationView.rx_navigationItemSelected(selected: Boolean): Observable<MenuItem> {
    return Observable.create { subscriber ->
        setNavigationItemSelectedListener {
            subscriber.onNext(it)
            selected
        }
        
        subscriber.setDisposable(AndroidMainThreadSubscription {
            setNavigationItemSelectedListener(null)
        })
    }
}

 
