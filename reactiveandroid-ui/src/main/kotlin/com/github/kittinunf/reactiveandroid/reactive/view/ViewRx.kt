package com.github.kittinunf.reactiveandroid.reactive.view

import android.graphics.Rect
import android.graphics.drawable.Drawable
import android.view.DragEvent
import android.view.KeyEvent
import android.view.MotionEvent
import android.view.View
import com.github.kittinunf.reactiveandroid.FieldDelegate
import com.github.kittinunf.reactiveandroid.internal.AndroidMainThreadDisposable
import com.github.kittinunf.reactiveandroid.reactive.AndroidBindingConsumer
import com.github.kittinunf.reactiveandroid.reactive.Reactive
import com.github.kittinunf.reactiveandroid.reactive.ofType
import io.reactivex.Observable
import io.reactivex.functions.Consumer

val View.rx: Reactive<View> by FieldDelegate({ Reactive(it) })

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

data class Padding(val start: Int, val top: Int, val end: Int, val bottom: Int)

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

//Events

fun Reactive<View>.click(): Observable<View> =
        Observable.create<View> { emitter ->
            item.setOnClickListener {
                if (!emitter.isDisposed) {
                    emitter.onNext(it)
                }
            }

            emitter.setDisposable(AndroidMainThreadDisposable { item.setOnClickListener(null) })
        }

data class DragListener(val view: View, val dragEvent: DragEvent)

fun Reactive<View>.drag(consumed: Boolean = true): Observable<DragListener> =
        Observable.create { emitter ->
            item.setOnDragListener { view, event ->
                if (!emitter.isDisposed) {
                    emitter.onNext(DragListener(view, event))
                }

                consumed
            }

            emitter.setDisposable(AndroidMainThreadDisposable { item.setOnDragListener(null) })
        }

data class KeyListener(val view: View, val keyCode: Int, val keyEvent: KeyEvent)

fun Reactive<View>.key(consumed: Boolean = true): Observable<KeyListener> =
        Observable.create { emitter ->
            item.setOnKeyListener { view, keyCode, keyEvent ->
                if (!emitter.isDisposed) {
                    emitter.onNext(KeyListener(view, keyCode, keyEvent))
                }

                consumed
            }

            emitter.setDisposable(AndroidMainThreadDisposable { item.setOnKeyListener(null) })
        }

data class MotionListener(val view: View, val motionEvent: MotionEvent)

fun Reactive<View>.hover(consumed: Boolean = true): Observable<MotionListener> =
        Observable.create { emitter ->
            item.setOnHoverListener { view, motionEvent ->
                if (!emitter.isDisposed) {
                    emitter.onNext(MotionListener(view, motionEvent))
                }

                consumed
            }

            emitter.setDisposable(AndroidMainThreadDisposable { item.setOnHoverListener(null) })
        }

fun Reactive<View>.touch(consumed: Boolean = true): Observable<MotionListener> =
        Observable.create { emitter ->
            item.setOnTouchListener { view, motionEvent ->
                if (!emitter.isDisposed) {
                    emitter.onNext(MotionListener(view, motionEvent))
                }

                consumed
            }

            emitter.setDisposable(AndroidMainThreadDisposable { item.setOnTouchListener(null) })
        }

fun Reactive<View>.longClick(consumed: Boolean = true): Observable<View> =
        Observable.create { emitter ->
            item.setOnLongClickListener {
                if (!emitter.isDisposed) {
                    emitter.onNext(it)
                }

                consumed
            }

            emitter.setDisposable(AndroidMainThreadDisposable { item.setOnLongClickListener(null) })
        }

data class FocusChangeListener(val view: View, val hasFocus: Boolean)

fun Reactive<View>.focusChange(): Observable<FocusChangeListener> =
        Observable.create { emitter ->
            item.setOnFocusChangeListener { view, hasFocus ->
                if (!emitter.isDisposed) {
                    emitter.onNext(FocusChangeListener(view, hasFocus))
                }
            }

            emitter.setDisposable(AndroidMainThreadDisposable { item.onFocusChangeListener = null })
        }

data class LayoutChangeListener(val view: View, val newRect: Rect, val oldRect: Rect)

fun Reactive<View>.layoutChange(): Observable<LayoutChangeListener> =
        Observable.create { emitter ->
            val listener = View.OnLayoutChangeListener { view, left, top, right, bottom, oldLeft, oldTop, oldRight, oldBottom ->
                if (!emitter.isDisposed) {
                    emitter.onNext(LayoutChangeListener(view, Rect(left, top, right, bottom), Rect(oldLeft, oldTop, oldRight, oldBottom)))
                }
            }
            item.addOnLayoutChangeListener(listener)

            emitter.setDisposable(AndroidMainThreadDisposable { item.removeOnLayoutChangeListener(listener) })
        }

data class ScrollDirection(val x: Int, val y: Int)
data class ScrollChangeListener(val view: View, val direction: ScrollDirection, val oldDirection: ScrollDirection)

fun Reactive<View>.scrollChange(): Observable<ScrollChangeListener> =
        Observable.create { emitter ->
            item.setOnScrollChangeListener { view: View, scrollX: Int, scrollY: Int, oldScrollX: Int, oldScrollY: Int ->
                if (!emitter.isDisposed) {
                    emitter.onNext(ScrollChangeListener(view, ScrollDirection(scrollX, scrollY), ScrollDirection(oldScrollX, oldScrollY)))
                }
            }

            emitter.setDisposable(AndroidMainThreadDisposable { item.setOnScrollChangeListener(null) })
        }

sealed class AttachStateChangeEvent {
    data class Attach(val view: View) : AttachStateChangeEvent()
    data class Detach(val view: View) : AttachStateChangeEvent()
}

fun Reactive<View>.attachedToWindow() = attachStateChange().ofType<AttachStateChangeEvent.Attach>()

fun Reactive<View>.detachedFromWindow() = attachStateChange().ofType<AttachStateChangeEvent.Detach>()

private fun Reactive<View>.attachStateChange(): Observable<AttachStateChangeEvent> =
        Observable.create { emitter ->
            val listener = object : View.OnAttachStateChangeListener {
                override fun onViewDetachedFromWindow(view: View) {
                    if (!emitter.isDisposed) {
                        emitter.onNext(AttachStateChangeEvent.Detach(view))
                    }
                }

                override fun onViewAttachedToWindow(view: View) {
                    if (!emitter.isDisposed) {
                        emitter.onNext(AttachStateChangeEvent.Attach(view))
                    }
                }
            }

            item.addOnAttachStateChangeListener(listener)

            emitter.setDisposable(AndroidMainThreadDisposable { item.removeOnAttachStateChangeListener(listener) })
        }