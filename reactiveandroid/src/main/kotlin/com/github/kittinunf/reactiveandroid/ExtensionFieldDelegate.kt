package com.github.kittinunf.reactiveandroid

import com.github.kittinunf.reactiveandroid.util.WeakIdentityHashMap
import kotlin.reflect.KProperty

class ExtensionFieldDelegate<in R, T : Any>(val initializer: (R) -> T, val builder: (R.(T) -> Unit)) {

    private val map = WeakIdentityHashMap<R, T>()

    operator fun getValue(ref: R, property: KProperty<*>): T = map.getOrPut(ref, { setValue(ref, property, initializer(ref)) })

    operator fun setValue(ref: R, property: KProperty<*>, value: T): T {
        ref.builder(value)
        map[ref] = value
        return value
    }

}
