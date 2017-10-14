package com.github.kittinunf.reactiveandroid.reactive.widget

import android.view.DragEvent
import android.view.View
import com.github.kittinunf.reactiveandroid.ExtensionFieldDelegate
import com.github.kittinunf.reactiveandroid.FieldDelegate
import com.github.kittinunf.reactiveandroid.internal.AndroidMainThreadDisposable
import com.github.kittinunf.reactiveandroid.reactive.Reactive
import io.reactivex.Observable

var View.rx: Reactive<View> by FieldDelegate { Reactive(it) }

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