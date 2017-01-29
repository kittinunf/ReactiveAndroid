package com.github.kittinunf.reactiveandroid.subscription

import rx.android.MainThreadSubscription

class AndroidMainThreadSubscription(private val unsubscribe: () -> Unit) : MainThreadSubscription() {

    override fun onUnsubscribe() {
        unsubscribe.invoke()
    }

}