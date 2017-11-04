package com.github.kittinunf.reactiveandroid.widget

import android.widget.CalendarView
import com.github.kittinunf.reactiveandroid.subscription.AndroidMainThreadSubscription
import io.reactivex.Observable

//================================================================================
// Events
//================================================================================

data class DateChangeListener(val view: CalendarView, val year: Int, val month: Int, val dayOfMonth: Int)

fun CalendarView.rx_dateChange(): Observable<DateChangeListener> {
    return Observable.create { subscriber ->
        setOnDateChangeListener { view, year, month, dayOfMonth ->
            subscriber.onNext(DateChangeListener(view, year, month, dayOfMonth))
        }
        
        subscriber.setDisposable(AndroidMainThreadSubscription {
            setOnDateChangeListener(null)
        })
    }
}
