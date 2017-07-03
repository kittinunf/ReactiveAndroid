package com.github.kittinunf.reactiveandroid.subscription

import io.reactivex.android.MainThreadDisposable

class AndroidMainThreadSubscription(private val unsubscribe: () -> Unit) : MainThreadDisposable() {
    override fun onDispose() {
        unsubscribe.invoke()
    }
}
