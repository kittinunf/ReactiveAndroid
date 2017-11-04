package com.github.kittinunf.reactiveandroid.view

import android.graphics.Rect
import android.view.ContextMenu
import android.view.DragEvent
import android.view.KeyEvent
import android.view.MotionEvent
import android.view.View
import com.github.kittinunf.reactiveandroid.ExtensionFieldDelegate
import com.github.kittinunf.reactiveandroid.reactive.AndroidBindingConsumer
import com.github.kittinunf.reactiveandroid.reactive.Reactive
import com.github.kittinunf.reactiveandroid.reactive.view.FocusChangeListener
import com.github.kittinunf.reactiveandroid.reactive.view.LayoutChangeListener
import com.github.kittinunf.reactiveandroid.reactive.view.ScrollChangeListener
import com.github.kittinunf.reactiveandroid.reactive.view.ScrollDirection
import com.github.kittinunf.reactiveandroid.subscription.AndroidMainThreadSubscription
import io.reactivex.Observable
import io.reactivex.functions.Consumer

//Properties


//Listeners

fun View.rx_click(): Observable<View> {
    return Observable.create { subscriber ->
        setOnClickListener {
            subscriber.onNext(it)
        }

        subscriber.setDisposable(AndroidMainThreadSubscription {
            setOnClickListener(null)
        })
    }
}

data class DragListener(val view: View, val dragEvent: DragEvent)

fun View.rx_drag(consumed: Boolean): Observable<DragListener> {
    return Observable.create { subscriber ->
        setOnDragListener { view, event ->
            subscriber.onNext(DragListener(view, event))
            consumed
        }

        subscriber.setDisposable(AndroidMainThreadSubscription {
            setOnDragListener(null)
        })
    }
}

data class KeyListener(val view: View, val keyCode: Int, val keyEvent: KeyEvent)

fun View.rx_key(consumed: Boolean): Observable<KeyListener> {
    return Observable.create { subscriber ->
        setOnKeyListener { view, code, event ->
            subscriber.onNext(KeyListener(view, code, event))
            consumed
        }

        subscriber.setDisposable(AndroidMainThreadSubscription {
            setOnKeyListener(null)
        })
    }
}

//fun View.rx_hover(consumed: Boolean): Observable<HoverListener> {
//    return Observable.create { subscriber ->
//        setOnHoverListener { view, event ->
//            subscriber.onNext(HoverListener(view, event))
//            consumed
//        }
//
//        subscriber.setDisposable(AndroidMainThreadSubscription {
//            setOnHoverListener(null)
//        })
//    }
//}

data class TouchListener(val view: View, val motionEvent: MotionEvent)

fun View.rx_touch(consumed: Boolean): Observable<TouchListener> {
    return Observable.create { subscriber ->
        setOnTouchListener { view, event ->
            subscriber.onNext(TouchListener(view, event))
            consumed
        }

        subscriber.setDisposable(AndroidMainThreadSubscription {
            setOnTouchListener(null)
        })
    }
}

fun View.rx_longClick(consumed: Boolean): Observable<View> {
    return Observable.create { subscriber ->
        setOnLongClickListener {
            subscriber.onNext(it)
            consumed
        }

        subscriber.setDisposable(AndroidMainThreadSubscription {
            setOnLongClickListener(null)
        })
    }
}


fun View.rx_focusChange(): Observable<FocusChangeListener> {
    return Observable.create { subscriber ->
        setOnFocusChangeListener { view, event ->
            subscriber.onNext(FocusChangeListener(view, event))
        }

        subscriber.setDisposable(AndroidMainThreadSubscription {
            onFocusChangeListener = null
        })
    }
}


fun View.rx_layoutChange(): Observable<LayoutChangeListener> {
    return Observable.create { subscriber ->
        val listener = View.OnLayoutChangeListener { view, left, top, right, bottom, oldLeft, oldTop, oldRight, oldBottom ->
            subscriber.onNext(LayoutChangeListener(view, Rect(left, top, right, bottom), Rect(oldLeft, oldTop, oldRight, oldBottom)))
        }
        addOnLayoutChangeListener(listener)

        subscriber.setDisposable(AndroidMainThreadSubscription {
            removeOnLayoutChangeListener(listener)
        })
    }
}



fun View.rx_scrollChange(): Observable<ScrollChangeListener> {
    return Observable.create { subscriber ->
        setOnScrollChangeListener { view, scrollX, scrollY, oldScrollX, oldScrollY ->
            subscriber.onNext(ScrollChangeListener(view, ScrollDirection(scrollX, scrollY), ScrollDirection(oldScrollX, oldScrollY)))
        }

        subscriber.setDisposable(AndroidMainThreadSubscription {
            setOnScrollChangeListener(null)
        })
    }
}

data class CreateContextMenuListener(val contextMenu: ContextMenu, val view: View, val menuInfo: ContextMenu.ContextMenuInfo)

fun View.rx_createContextMenu(): Observable<CreateContextMenuListener> {
    return Observable.create { subscriber ->
        setOnCreateContextMenuListener { menu, view, menuInfo ->
            subscriber.onNext(CreateContextMenuListener(menu, view, menuInfo))
        }

        subscriber.setDisposable(AndroidMainThreadSubscription {
            setOnCreateContextMenuListener(null)
        })
    }
}

fun View.rx_attachedToWindow(): Observable<View> {
    return Observable.create { subscriber ->
        _attachStateChange.onViewAttachedToWindow {
            if (it != null) subscriber.onNext(it)
        }

        subscriber.setDisposable(AndroidMainThreadSubscription {
            removeOnAttachStateChangeListener(_attachStateChange)
        })
    }
}

fun View.rx_detachedFromWindow(): Observable<View> {
    return Observable.create { subscriber ->
        _attachStateChange.onViewDetachedFromWindow {
            if (it != null) subscriber.onNext(it)
        }

        subscriber.setDisposable(AndroidMainThreadSubscription {
            removeOnAttachStateChangeListener(_attachStateChange)
        })
    }
}

private val View._attachStateChange: _View_AttachStateChangeListener
        by ExtensionFieldDelegate({ _View_AttachStateChangeListener() }, { addOnAttachStateChangeListener(it) })

private class _View_AttachStateChangeListener : View.OnAttachStateChangeListener {

    private var onViewDetachedFromWindow: ((View?) -> Unit)? = null

    private var onViewAttachedToWindow: ((View?) -> Unit)? = null

    fun onViewDetachedFromWindow(listener: (View?) -> Unit) {
        onViewDetachedFromWindow = listener
    }

    override fun onViewDetachedFromWindow(v: View?) {
        onViewDetachedFromWindow?.invoke(v)
    }

    fun onViewAttachedToWindow(listener: (View?) -> Unit) {
        onViewAttachedToWindow = listener
    }

    override fun onViewAttachedToWindow(v: View?) {
        onViewAttachedToWindow?.invoke(v)
    }

}


