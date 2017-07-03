package com.github.kittinunf.reactiveandroid

import io.reactivex.Scheduler

inline fun <T> createMutableProperty(getter: () -> T, crossinline setter: (T) -> Unit, observeScheduler: Scheduler): MutableProperty<T> {
    return MutableProperty(getter()).apply {
        observable.observeOn(observeScheduler).subscribe {
            setter(it)
        }
    }
}

inline fun <reified T : Any> createMutableProperty(crossinline setter: (T) -> Unit, observeScheduler: Scheduler): MutableProperty<T> {
    val t = T::class.java.newInstance()
    return MutableProperty(t).apply {
        observable.observeOn(observeScheduler).subscribe {
            setter(it)
        }
    }
}
