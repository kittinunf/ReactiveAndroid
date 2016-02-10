package com.github.kittinunf.reactiveandroid.subscription

import android.os.Looper
import com.github.kittinunf.reactiveandroid.scheduler.AndroidThreadScheduler
import rx.Subscription
import rx.subscriptions.BooleanSubscription

class AndroidMainThreadSubscription(private val unsubscriber: () -> Unit) : Subscription {

    val subscription = BooleanSubscription.create {
        if (Looper.myLooper() == Looper.getMainLooper()) {
            unsubscriber()
        
            AndroidThreadScheduler.mainThreadScheduler.createWorker()?.schedule(unsubscriber)
        }
    }

    override fun isUnsubscribed() = subscription.isUnsubscribed

    override fun unsubscribe() {
        subscription.unsubscribe()
    }

}