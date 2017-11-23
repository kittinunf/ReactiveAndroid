package com.github.kittinunf.reactiveandroid.reactive.view

import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.net.Uri
import android.widget.ImageView
import com.github.kittinunf.reactiveandroid.FieldDelegate
import com.github.kittinunf.reactiveandroid.reactive.AndroidBindingConsumer
import com.github.kittinunf.reactiveandroid.reactive.Reactive
import io.reactivex.functions.Consumer

val ImageView.rx: Reactive<ImageView> by FieldDelegate({ Reactive(it) })

// Properties

val Reactive<ImageView>.drawable: Consumer<Drawable>
    get() = AndroidBindingConsumer(item) { item, value ->
        item.setImageDrawable(value)
    }

val Reactive<ImageView>.bitmap: Consumer<Bitmap>
    get() = AndroidBindingConsumer(item) { item, value ->
        item.setImageBitmap(value)
    }

val Reactive<ImageView>.resourceId: Consumer<Int>
    get() = AndroidBindingConsumer(item) { item, value ->
        item.setImageResource(value)
    }

val Reactive<ImageView>.uri: Consumer<Uri>
    get() = AndroidBindingConsumer(item) { item, value ->
        item.setImageURI(value)
    }

val Reactive<ImageView>.scaleType: Consumer<ImageView.ScaleType>
    get() = AndroidBindingConsumer(item) { item, value ->
        item.scaleType = value
    }