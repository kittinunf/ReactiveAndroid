package com.github.kittinunf.reactiveandroid.reactive.view

import android.graphics.drawable.Drawable
import android.view.DragEvent
import android.view.View
import com.github.kittinunf.reactiveandroid.ExtensionFieldDelegate
import com.github.kittinunf.reactiveandroid.FieldDelegate
import com.github.kittinunf.reactiveandroid.internal.AndroidMainThreadDisposable
import com.github.kittinunf.reactiveandroid.reactive.AndroidBindingConsumer
import com.github.kittinunf.reactiveandroid.reactive.Reactive
import com.github.kittinunf.reactiveandroid.view.Padding
import io.reactivex.Observable
import io.reactivex.functions.Consumer

val View.rx: Reactive<View> by FieldDelegate { Reactive(it) }

//Properties
val Reactive<View>.activated: Consumer<Boolean>
    get() = AndroidBindingConsumer(item) { item, value ->
        item.isActivated = value
    }

val Reactive<View>.alpha: Consumer<Float>
    get() = AndroidBindingConsumer(item) { item, value ->
        item.alpha = value
    }

val Reactive<View>.background: Consumer<Drawable>
    get() = AndroidBindingConsumer(item) { item, value ->
        item.background = value
    }

val Reactive<View>.backgroundColor: Consumer<Int>
    get() = AndroidBindingConsumer(item) { item, value ->
        item.setBackgroundColor(value)
    }

val Reactive<View>.clickable: Consumer<Boolean>
    get() = AndroidBindingConsumer(item) { item, value ->
        item.isClickable = value
    }

val Reactive<View>.enabled: Consumer<Boolean>
    get() = AndroidBindingConsumer(item) { item, value ->
        item.isEnabled = value
    }

val Reactive<View>.focused: Consumer<Boolean>
    get() = AndroidBindingConsumer(item) { item, value ->
        if (value) item.requestFocus() else item.clearFocus()
    }

val Reactive<View>.longClickable: Consumer<Boolean>
    get() = AndroidBindingConsumer(item) { item, value ->
        item.isLongClickable = value
    }

val Reactive<View>.padding: Consumer<Padding>
    get() = AndroidBindingConsumer(item) { item, value ->
        item.setPaddingRelative(value.start, value.top, value.end, value.bottom)
    }

val Reactive<View>.pressed: Consumer<Boolean>
    get() = AndroidBindingConsumer(item) { item, value ->
        item.isPressed = value
    }

val Reactive<View>.selected: Consumer<Boolean>
    get() = AndroidBindingConsumer(item) { item, value ->
        item.isSelected = value
    }

val Reactive<View>.visible: Consumer<Boolean>
    get() = AndroidBindingConsumer(item) { item, value ->
        item.visibility = if (value) View.VISIBLE else View.GONE
    }

val Reactive<View>.visibility: Consumer<Int>
    get() = AndroidBindingConsumer(item) { item, value ->
        item.visibility = value
    }

fun Reactive<View>.click() = Observable.create<View> { emitter ->

    val listener = item._click
    listener.onClick {
        emitter.onNext(it)
    }

    emitter.setDisposable(AndroidMainThreadDisposable({ item.setOnClickListener(null) }))
}

private val View._click: _View_OnClickListener
        by ExtensionFieldDelegate({ _View_OnClickListener() }, { setOnClickListener(it) })

private class _View_OnClickListener : View.OnClickListener {

    private var _onClick: ((View) -> Unit)? = null

    override fun onClick(view: View) {
        _onClick?.invoke(view)
    }

    fun onClick(listener: (View) -> Unit) {
        _onClick = listener
    }

}

data class DragListener(val view: View, val dragEvent: DragEvent)

fun Reactive<View>.drag(consumed: Boolean) = Observable.create<DragListener> { emitter ->
    val listener = item._drag

    listener.onDrag {
        emitter.onNext(it)
        consumed
    }

    emitter.setDisposable(AndroidMainThreadDisposable({ item.setOnDragListener(null) }))
}

private val View._drag: _View_OnDragListener
        by ExtensionFieldDelegate({ _View_OnDragListener() }, { setOnDragListener(it) })

private class _View_OnDragListener : View.OnDragListener {

    private var _onDrag: ((DragListener) -> Boolean)? = null

    override fun onDrag(p0: View, p1: DragEvent): Boolean
            = _onDrag?.invoke(DragListener(p0, p1)) ?: false

    fun onDrag(listener: (DragListener) -> Boolean) {
        _onDrag = listener
    }

}

//TODO: KeyListener

//TODO: HoverListener

//TODO: TouchListener

//TODO: LongClick

//TODO: FocusChangeListener

//TODO: LayoutChangeListener

//TODO: ScrollChangeListener

//TODO: CreateContextMenuListener

//TODO: AttachToWindow

//TODO: DetachFromWindow