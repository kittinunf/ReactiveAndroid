package com.github.kittinunf.reactiveandroid.widget

import android.widget.RatingBar
import com.github.kittinunf.reactiveandroid.subscription.AndroidMainThreadSubscription
import rx.Observable

//================================================================================
// Events 
//================================================================================

data class RatingBarChangeListener(val ratingBar: RatingBar, val rating: Float, val fromUser: Boolean)

fun RatingBar.rx_ratingBarChange(): Observable<RatingBarChangeListener> {
    return Observable.create { subscriber ->
        setOnRatingBarChangeListener { ratingBar, rating, fromUser ->
            subscriber.onNext(RatingBarChangeListener(ratingBar, rating, fromUser))
        }

        subscriber.add(AndroidMainThreadSubscription {
            onRatingBarChangeListener = null
        })
    }
}

 
