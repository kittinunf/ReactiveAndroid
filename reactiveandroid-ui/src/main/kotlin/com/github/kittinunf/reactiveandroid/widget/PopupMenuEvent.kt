package com.github.kittinunf.reactiveandroid.widget

import android.view.MenuItem
import android.widget.PopupMenu
import com.github.kittinunf.reactiveandroid.subscription.AndroidMainThreadSubscription
import rx.Observable

//================================================================================
// Events 
//================================================================================

fun PopupMenu.rx_dismiss(): Observable<PopupMenu> {
    return Observable.create { subscriber ->
        setOnDismissListener {
            subscriber.onNext(it)

        }
        
        subscriber.add(AndroidMainThreadSubscription {
            setOnDismissListener(null)
        })
    }
}

fun PopupMenu.rx_menuItemClick(consumed: Boolean): Observable<MenuItem> {
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
