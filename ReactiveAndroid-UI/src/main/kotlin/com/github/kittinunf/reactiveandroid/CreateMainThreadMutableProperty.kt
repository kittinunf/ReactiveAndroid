package com.github.kittinunf.reactiveandroid

import com.github.kittinunf.reactiveandroid.scheduler.AndroidThreadScheduler

fun <T> createMainThreadMutableProperty(getter: () -> T, setter: (T) -> Unit) =
        createMutableProperty(getter, setter, AndroidThreadScheduler.mainThreadScheduler)
