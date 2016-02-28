package com.github.kittinunf.reactiveandroid.widget

import android.widget.DatePicker
import rx.Observable

//================================================================================
// Events 
//================================================================================

data class DateChangedListener(val picker: DatePicker, val year: Int, val monthOfYear: Int, val dayOfMonth: Int)

data class DateState(val year: Int, val monthOfYear: Int, val dayOfMonth: Int)

fun DatePicker.rx_dateChanged(state: DateState): Observable<DateChangedListener> {
    return Observable.create { subscriber ->
        
        init(state.year, state.monthOfYear, state.dayOfMonth) { picker, year, monthOfYear, dayOfMonth ->
            subscriber.onNext(DateChangedListener(picker, year, monthOfYear, dayOfMonth))
        }

    }
}


 
