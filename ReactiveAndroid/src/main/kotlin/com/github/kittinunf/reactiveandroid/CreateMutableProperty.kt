package com.github.kittinunf.reactiveandroid

import rx.Scheduler

/**
 * Created by Kittinun Vantasin on 1/31/16.
 */

fun <T> createMutableProperty(getter: () -> T, setter: (T) -> Unit, observeScheduler: Scheduler): MutableProperty<T> {
    return MutableProperty(getter()).apply {
        observable.observeOn(observeScheduler).subscribe {
            setter(it)
        }
    }
}
