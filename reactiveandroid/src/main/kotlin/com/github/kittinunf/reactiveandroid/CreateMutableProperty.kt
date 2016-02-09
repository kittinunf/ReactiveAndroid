package com.github.kittinunf.reactiveandroid

import rx.Scheduler

fun <T> createMutableProperty(getter: () -> T, setter: (T) -> Unit, observeScheduler: Scheduler): MutableProperty<T> {
    return MutableProperty(getter()).apply {
        observable.observeOn(observeScheduler).subscribe {
            setter(it)
        }
    }
}
