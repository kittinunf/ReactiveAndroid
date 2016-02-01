package com.github.kittinunf.reactiveandroid.view

import android.view.View
import com.github.kittinunf.reactiveandroid.*
import rx.Observable

//================================================================================
// Events
//================================================================================

fun View.rx_click(): Observable<View> {
    return Observable.create { subscriber ->
        if (hasOnClickListeners()) {
            setOnClickListener(null)
        }

        setOnClickListener {
            subscriber.onNext(it)
        }
    }
}

fun View.rx_drag(consumed: Boolean): Observable<DragListener> {
    return Observable.create { subscriber ->
        setOnDragListener { view, event ->
            subscriber.onNext(DragListener(view, event))
            consumed
        }
    }
}

fun View.rx_key(consumed: Boolean): Observable<KeyListener> {
    return Observable.create { subscriber ->
        setOnKeyListener { view, code, event ->
            subscriber.onNext(KeyListener(view, code, event))
            consumed
        }
    }
}

fun View.rx_hover(consumed: Boolean): Observable<HoverListener> {
    return Observable.create { subscriber ->
        setOnHoverListener { view, event ->
            subscriber.onNext(HoverListener(view, event))
            consumed
        }
    }
}

fun View.rx_touch(consumed: Boolean): Observable<TouchListener> {
    return Observable.create { subscriber ->
        setOnTouchListener { view, event ->
            subscriber.onNext(TouchListener(view, event))
            consumed
        }
    }
}

fun View.rx_longClick(consumed: Boolean): Observable<View> {
    return Observable.create { subscriber ->
        setOnLongClickListener {
            subscriber.onNext(it)
            consumed
        }
    }
}

fun View.rx_focusChange(): Observable<FocusChangeListener> {
    return Observable.create { subscriber ->
        setOnFocusChangeListener { view, event ->
            subscriber.onNext(FocusChangeListener(view, event))
        }
    }
}

fun View.rx_layoutChange(): Observable<LayoutChangeListener> {
    return Observable.create { subscriber ->
        addOnLayoutChangeListener { view, left, top, right, bottom, oldLeft, oldTop, oldRight, oldBottom ->
            subscriber.onNext(LayoutChangeListener(view, Padding(left, top, right, bottom), Padding(oldLeft, oldTop, oldRight, oldBottom)))
        }
    }
}

fun View.rx_scrollChange(): Observable<ScrollChangeListener> {
    return Observable.create { subscriber ->
        setOnScrollChangeListener { view, scrollX, scrollY, oldScrollX, oldScrollY ->
            subscriber.onNext(ScrollChangeListener(view, ScrollDirection(scrollX, scrollY), ScrollDirection(oldScrollX, oldScrollY)))
        }
    }
}

fun View.rx_createContextMenu(): Observable<CreateContextMenuListener> {
    return Observable.create { subscriber ->
        setOnCreateContextMenuListener { menu, view, menuInfo ->
            subscriber.onNext(CreateContextMenuListener(menu, view, menuInfo))
        }
    }
}



