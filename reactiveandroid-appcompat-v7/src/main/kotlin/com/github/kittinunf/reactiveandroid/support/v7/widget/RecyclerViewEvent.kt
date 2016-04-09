package com.github.kittinunf.reactiveandroid.support.v7.widget

import android.support.v7.widget.RecyclerView
import android.view.MotionEvent
import android.view.View
import com.github.kittinunf.reactiveandroid.subscription.AndroidMainThreadSubscription
import rx.Observable

//================================================================================
// Events
//================================================================================

data class ScrolledListener(val recyclerView: RecyclerView?, val dx: Int, val dy: Int)

fun RecyclerView.rx_scrolled(): Observable<ScrolledListener> {
    return Observable.create { subscriber ->
        _scroll.onScrolled { recyclerView, dx, dy ->
            subscriber.onNext(ScrolledListener(recyclerView, dx, dy))
        }

        subscriber.add(AndroidMainThreadSubscription {
            removeOnScrollListener(_scroll)
        })
    }
}

data class ScrollStateChangedListener(val recyclerView: RecyclerView?, val newState: Int)

fun RecyclerView.rx_scrollStateChanged(): Observable<ScrollStateChangedListener> {
    return Observable.create { subscriber ->
        _scroll.onScrollStateChanged { recyclerView, newState ->
            subscriber.onNext(ScrollStateChangedListener(recyclerView, newState))
        }

        subscriber.add(AndroidMainThreadSubscription {
            removeOnScrollListener(_scroll)
        })
    }
}

fun RecyclerView.rx_recycler(): Observable<RecyclerView.ViewHolder> {
    return Observable.create { subscriber ->
        setRecyclerListener {
            subscriber.onNext(it)
        }

        subscriber.add(AndroidMainThreadSubscription {
            setRecyclerListener(null)
        })
    }
}

fun RecyclerView.rx_onChildViewAttachedToWindow(): Observable<View> {
    return Observable.create { subscriber ->
        _childAttachStateChange.onChildViewAttachedToWindow {
            subscriber.onNext(it)
        }

        subscriber.add(AndroidMainThreadSubscription {
            removeOnChildAttachStateChangeListener(_childAttachStateChange)
        })
    }
}

fun RecyclerView.rx_onChildViewDetachedFromWindow(): Observable<View> {
    return Observable.create { subscriber ->
        _childAttachStateChange.onChildViewDetachedFromWindow {
            subscriber.onNext(it)
        }

        subscriber.add(AndroidMainThreadSubscription {
            removeOnChildAttachStateChangeListener(_childAttachStateChange)
        })
    }
}

data class TouchEventListener(val recyclerView: RecyclerView?, val event: MotionEvent?)

fun RecyclerView.rx_touchEvent() : Observable<TouchEventListener> {
    return Observable.create { subscriber ->
        _itemTouch.onTouchEvent { recyclerView, motionEvent ->
            subscriber.onNext(TouchEventListener(recyclerView, motionEvent))
        }

        subscriber.add(AndroidMainThreadSubscription {
            removeOnItemTouchListener(_itemTouch)
        })
    }
}

fun RecyclerView.rx_dataChanged() : Observable<Unit> {
    return Observable.create { subscriber ->
        _adapterDataObserver.onChanged {
            subscriber.onNext(Unit)
        }

        subscriber.add(AndroidMainThreadSubscription {
            adapter?.let {
                it.unregisterAdapterDataObserver(_adapterDataObserver)
            }
        })
    }
}

private val RecyclerView._itemTouch: _RecyclerView_OnItemTouchListener
    get() {
        val listener = _RecyclerView_OnItemTouchListener()
        addOnItemTouchListener(listener)
        return listener
    }

internal class _RecyclerView_OnItemTouchListener : RecyclerView.OnItemTouchListener {

    private var onTouchEvent: ((RecyclerView?, MotionEvent?) -> Unit)? = null

    private var onInterceptTouchEvent: ((RecyclerView?, MotionEvent?) -> Boolean)? = null

    private var onRequestDisallowInterceptTouchEvent: ((Boolean) -> Unit)? = null

    fun onTouchEvent(listener: (RecyclerView?, MotionEvent?) -> Unit) {
        onTouchEvent = listener
    }

    override fun onTouchEvent(recyclerView: RecyclerView?, event: MotionEvent?) {
        onTouchEvent?.invoke(recyclerView, event)
    }

    fun onInterceptTouchEvent(listener: (RecyclerView?, MotionEvent?) -> Boolean) {
        onInterceptTouchEvent = listener
    }

    override fun onInterceptTouchEvent(recyclerView: RecyclerView?, event: MotionEvent?): Boolean {
        return onInterceptTouchEvent?.invoke(recyclerView, event) ?: false
    }

    fun onRequestDisallowInterceptTouchEvent(listener: (Boolean) -> Unit) {
        onRequestDisallowInterceptTouchEvent = listener
    }

    override fun onRequestDisallowInterceptTouchEvent(disallowIntercept: Boolean) {
        onRequestDisallowInterceptTouchEvent?.invoke(disallowIntercept)
    }

}

private val RecyclerView._childAttachStateChange: _RecyclerView_OnChildAttachStateChangeListener
    get() {
        val listener = _RecyclerView_OnChildAttachStateChangeListener()
        addOnChildAttachStateChangeListener(listener)
        return listener
    }

internal class _RecyclerView_OnChildAttachStateChangeListener : RecyclerView.OnChildAttachStateChangeListener {

    private var onChildViewDetachedFromWindow: ((View?) -> Unit)? = null

    private var onChildViewAttachedToWindow: ((View?) -> Unit)? = null

    fun onChildViewDetachedFromWindow(listener: (View?) -> Unit) {
        onChildViewDetachedFromWindow = listener
    }

    override fun onChildViewDetachedFromWindow(view: View?) {
        onChildViewDetachedFromWindow?.invoke(view)
    }

    fun onChildViewAttachedToWindow(listener: (View?) -> Unit) {
        onChildViewAttachedToWindow = listener
    }

    override fun onChildViewAttachedToWindow(view: View?) {
        onChildViewAttachedToWindow?.invoke(view)
    }

}

private val RecyclerView._scroll: _RecyclerView_OnScrollListener
    get() {
        val listener = _RecyclerView_OnScrollListener()
        addOnScrollListener(listener)
        return listener
    }

internal class _RecyclerView_OnScrollListener : RecyclerView.OnScrollListener() {

    private var onScrollStateChanged: ((RecyclerView?, Int) -> Unit)? = null

    private var onScrolled: ((RecyclerView?, Int, Int) -> Unit)? = null

    fun onScrollStateChanged(listener: (RecyclerView?, Int) -> Unit) {
        onScrollStateChanged = listener
    }

    override fun onScrollStateChanged(recyclerView: RecyclerView?, newState: Int) {
        onScrollStateChanged?.invoke(recyclerView, newState)
    }

    fun onScrolled(listener: (RecyclerView?, Int, Int) -> Unit) {
        onScrolled = listener
    }

    override fun onScrolled(recyclerView: RecyclerView?, dx: Int, dy: Int) {
        onScrolled?.invoke(recyclerView, dx, dy)
    }

}

private val RecyclerView._adapterDataObserver: _RecyclerView_AdapterDataObserver
    get() {
        val listener = _RecyclerView_AdapterDataObserver()
        adapter?.let {
            it.registerAdapterDataObserver(listener)
        }
        return listener
    }

internal class _RecyclerView_AdapterDataObserver : RecyclerView.AdapterDataObserver() {

    private var onChanged: (() -> Unit)? = null

    private var onItemRangeInserted: ((Int, Int) -> Unit)? = null

    private var onItemRangeMoved: ((Int, Int, Int) -> Unit)? = null

    private var onItemRangeRemoved: ((Int, Int) -> Unit)? = null

    fun onChanged(listener: () -> Unit) {
        onChanged = listener
    }

    override fun onChanged() {
        onChanged?.invoke()
    }

    fun onItemRangeInserted(listener: (Int, Int) -> Unit) {
        onItemRangeInserted = listener
    }

    override fun onItemRangeInserted(positionStart: Int, itemCount: Int) {
        onItemRangeInserted?.invoke(positionStart, itemCount)
    }

    fun onItemRangeMoved(listener: (Int, Int, Int) -> Unit) {
        onItemRangeMoved = listener
    }

    override fun onItemRangeMoved(fromPosition: Int, toPosition: Int, itemCount: Int) {
        onItemRangeMoved?.invoke(fromPosition, toPosition, itemCount)
    }

    fun onItemRangeRemoved(listener: (Int, Int) -> Unit) {
        onItemRangeRemoved = listener
    }

    override fun onItemRangeRemoved(positionStart: Int, itemCount: Int) {
        onItemRangeRemoved?.invoke(positionStart, itemCount)
    }

}