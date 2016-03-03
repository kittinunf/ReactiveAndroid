package com.github.kittinunf.reactiveandroid.view

import android.view.*
import com.github.kittinunf.reactiveandroid.subscription.AndroidMainThreadSubscription
import rx.Observable

//================================================================================
// Events
//================================================================================

fun View.rx_click(): Observable<View> {
    return Observable.create { subscriber ->
        setOnClickListener {
            subscriber.onNext(it)
        }

        subscriber.add(AndroidMainThreadSubscription {
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

        subscriber.add(AndroidMainThreadSubscription {
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

        subscriber.add(AndroidMainThreadSubscription {
            setOnKeyListener(null)
        })
    }
}

data class HoverListener(val view: View, val motionEvent: MotionEvent)

fun View.rx_hover(consumed: Boolean): Observable<HoverListener> {
    return Observable.create { subscriber ->
        setOnHoverListener { view, event ->
            subscriber.onNext(HoverListener(view, event))
            consumed
        }

        subscriber.add(AndroidMainThreadSubscription {
            setOnHoverListener(null)
        })
    }
}

data class TouchListener(val view: View, val motionEvent: MotionEvent)

fun View.rx_touch(consumed: Boolean): Observable<TouchListener> {
    return Observable.create { subscriber ->
        setOnTouchListener { view, event ->
            subscriber.onNext(TouchListener(view, event))
            consumed
        }

        subscriber.add(AndroidMainThreadSubscription {
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

        subscriber.add(AndroidMainThreadSubscription {
            setOnLongClickListener(null)
        })
    }
}

data class FocusChangeListener(val view: View, val hasFocus: Boolean)

fun View.rx_focusChange(): Observable<FocusChangeListener> {
    return Observable.create { subscriber ->
        setOnFocusChangeListener { view, event ->
            subscriber.onNext(FocusChangeListener(view, event))
        }

        subscriber.add(AndroidMainThreadSubscription {
            onFocusChangeListener = null
        })
    }
}

data class LayoutChangeListener(val view: View, val newPadding: Padding, val oldPadding: Padding)

fun View.rx_layoutChange(): Observable<LayoutChangeListener> {
    return Observable.create { subscriber ->
        val listener: (View, Int, Int, Int, Int, Int, Int, Int, Int) -> Unit = {
            view, left, top, right, bottom, oldLeft, oldTop, oldRight, oldBottom ->
            subscriber.onNext(LayoutChangeListener(view, Padding(left, top, right, bottom), Padding(oldLeft, oldTop, oldRight, oldBottom)))
        }
        addOnLayoutChangeListener(listener)

        subscriber.add(AndroidMainThreadSubscription {
            removeOnLayoutChangeListener(listener)
        })
    }
}

data class ScrollDirection(val x: Int, val y: Int)

data class ScrollChangeListener(val view: View, val direction: ScrollDirection, val oldDirection: ScrollDirection)

fun View.rx_scrollChange(): Observable<ScrollChangeListener> {
    return Observable.create { subscriber ->
        setOnScrollChangeListener { view, scrollX, scrollY, oldScrollX, oldScrollY ->
            subscriber.onNext(ScrollChangeListener(view, ScrollDirection(scrollX, scrollY), ScrollDirection(oldScrollX, oldScrollY)))
        }

        subscriber.add(AndroidMainThreadSubscription {
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

        subscriber.add(AndroidMainThreadSubscription {
            setOnCreateContextMenuListener(null)
        })
    }
}



