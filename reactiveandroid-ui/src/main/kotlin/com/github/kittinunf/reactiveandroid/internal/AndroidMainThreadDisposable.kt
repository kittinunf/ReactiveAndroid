package com.github.kittinunf.reactiveandroid.internal

import io.reactivex.android.MainThreadDisposable

class AndroidMainThreadDisposable(private val disposable: () -> Unit) : MainThreadDisposable() {
    override fun onDispose() {
        disposable()
    }
}
