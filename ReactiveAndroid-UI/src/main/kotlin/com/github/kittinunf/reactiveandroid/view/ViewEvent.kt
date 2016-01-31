package com.github.kittinunf.reactiveandroid.view

import android.util.Log
import android.view.View
import rx.Observable

//================================================================================
// Events
//================================================================================

fun View.click(): Observable<View> {
    return Observable.create { subscriber ->
        if (hasOnClickListeners()) {
            Log.w("ReactiveAndroid", "View has an attached OnClickListener, it will be overridden. Please Consider remove it")
            setOnClickListener(null)
        }

        setOnClickListener {
            subscriber.onNext(it)
        }
    }
}