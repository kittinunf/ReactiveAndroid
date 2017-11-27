package com.github.kittinunf.reactiveandroid.reactive.view

import android.graphics.drawable.Drawable
import android.view.MenuItem
import android.view.View
import com.github.kittinunf.reactiveandroid.FieldDelegate
import com.github.kittinunf.reactiveandroid.reactive.AndroidBindingConsumer
import com.github.kittinunf.reactiveandroid.reactive.Reactive
import io.reactivex.functions.Consumer

val MenuItem.rx: Reactive<MenuItem> by FieldDelegate({ Reactive(it) })

// Properties

val Reactive<MenuItem>.actionView: Consumer<View>
    get() = AndroidBindingConsumer(item) { item, value ->
        item.actionView = value
    }

val Reactive<MenuItem>.actionViewResourceId: Consumer<Int>
    get() = AndroidBindingConsumer(item) { item, value ->
        item.setActionView(value)
    }

val Reactive<MenuItem>.icon: Consumer<Drawable>
    get() = AndroidBindingConsumer(item) { item, value ->
        item.icon = value
    }

val Reactive<MenuItem>.enabled: Consumer<Boolean>
    get() = AndroidBindingConsumer(item) { item, value ->
        item.isEnabled = value
    }

val Reactive<MenuItem>.visible: Consumer<Boolean>
    get() = AndroidBindingConsumer(item) { item, value ->
        item.isVisible = value
    }

val Reactive<MenuItem>.title: Consumer<CharSequence>
    get() = AndroidBindingConsumer(item) { item, value ->
        item.title = value
    }


