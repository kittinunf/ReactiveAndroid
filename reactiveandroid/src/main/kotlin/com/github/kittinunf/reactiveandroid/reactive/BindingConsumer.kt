package com.github.kittinunf.reactiveandroid.reactive

import io.reactivex.functions.Consumer

open class BindingConsumer<E, T>(val item: E,
                                 val binder: (E, T) -> Unit) : Consumer<T> {
    override fun accept(t: T) {
        scope {
            binder(item, t)
        }
    }

    protected open fun scope(f: () -> Unit) = f()
}
