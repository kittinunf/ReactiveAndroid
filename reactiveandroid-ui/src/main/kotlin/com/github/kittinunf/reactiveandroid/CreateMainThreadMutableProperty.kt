package com.github.kittinunf.reactiveandroid

import com.github.kittinunf.reactiveandroid.scheduler.AndroidThreadScheduler

inline fun <T> createMainThreadMutableProperty(getter: () -> T, crossinline setter: (T) -> Unit) =
        createMutableProperty(getter, setter, AndroidThreadScheduler.mainThreadScheduler)

inline fun <reified T: Any> createMainThreadMutableProperty(crossinline setter: (T) -> Unit) =
        createMutableProperty(setter, AndroidThreadScheduler.mainThreadScheduler)
