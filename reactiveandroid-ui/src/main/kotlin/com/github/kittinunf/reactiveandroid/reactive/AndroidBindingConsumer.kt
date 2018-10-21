package com.github.kittinunf.reactiveandroid.reactive

import android.os.Handler
import android.os.Looper

class AndroidBindingConsumer<out E, T>(item: E,
                                       binder: (E, T) -> Unit) : BindingConsumer<E, T>(item, binder) {
    override fun scope(f: () -> Unit) {
        if (Threads.mainThread == Thread.currentThread()) {
            f()
        } else {
            Threads.mainThreadHandler.post(f)
        }
    }
}

private object Threads {
    val mainThreadHandler = Handler(Looper.getMainLooper())
    val mainThread: Thread = Looper.getMainLooper().thread
}
