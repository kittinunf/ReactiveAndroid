package com.github.kittinunf.reactiveandroid

import com.github.kittinunf.reactiveandroid.util.WeakIdentityHashMap
import kotlin.reflect.KProperty

class ExtensionFieldDelegate<in R, T : Any>(private val initializer: (R) -> T,
                                            private val builder: (R.(T) -> Unit)) {

    private val map = WeakIdentityHashMap<R, T>()

    operator fun getValue(ref: R, property: KProperty<*>): T =
            map.getOrPut(ref, { setValue(ref, property, initializer(ref)) })

    operator fun setValue(ref: R, property: KProperty<*>, value: T): T {
        ref.builder(value)
        map[ref] = value
        return value
    }

}

class FieldDelegate<in T, U : Any>(private val initializer: (T) -> U) {

    private val map = WeakIdentityHashMap<T, U>()

    operator fun getValue(ref: T, property: KProperty<*>): U =
            map.getOrPut(ref, { setValue(ref, property, initializer(ref)) })

    operator fun setValue(ref: T, property: KProperty<*>, value: U): U {
        map[ref] = value
        return value
    }

}
