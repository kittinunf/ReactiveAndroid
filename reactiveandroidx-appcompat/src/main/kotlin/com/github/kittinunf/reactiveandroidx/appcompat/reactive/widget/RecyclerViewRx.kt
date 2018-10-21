package com.github.kittinunf.reactiveandroidx.appcompat.reactive.widget

import android.view.MotionEvent
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.github.kittinunf.reactiveandroid.FieldDelegate
import com.github.kittinunf.reactiveandroid.internal.AndroidMainThreadDisposable
import com.github.kittinunf.reactiveandroid.reactive.AndroidBindingConsumer
import com.github.kittinunf.reactiveandroid.reactive.Reactive
import com.github.kittinunf.reactiveandroid.reactive.ofType
import io.reactivex.Observable
import io.reactivex.functions.Consumer

val RecyclerView.rx: Reactive<RecyclerView> by FieldDelegate({ Reactive(it) })

// Properties

val Reactive<RecyclerView>.adapter: Consumer<RecyclerView.Adapter<*>>
    get() = AndroidBindingConsumer(item) { item, value ->
        item.adapter = value
    }

val Reactive<RecyclerView>.itemAnimator: Consumer<RecyclerView.ItemAnimator>
    get() = AndroidBindingConsumer(item) { item, value ->
        item.itemAnimator = value
    }

val Reactive<RecyclerView>.layoutManager: Consumer<RecyclerView.LayoutManager>
    get() = AndroidBindingConsumer(item) { item, value ->
        item.layoutManager = value
    }

// Listeners

sealed class RecyclerViewScrollEvent {
    data class ScrollStateChanged(val recyclerView: RecyclerView, val newState: Int) : RecyclerViewScrollEvent()
    data class Scrolled(val recyclerView: RecyclerView, val dx: Int, val dy: Int) : RecyclerViewScrollEvent()
}

fun Reactive<RecyclerView>.scrollStateChanged() = scroll().ofType<RecyclerViewScrollEvent.ScrollStateChanged>()

fun Reactive<RecyclerView>.scrolled() = scroll().ofType<RecyclerViewScrollEvent.Scrolled>()

private fun Reactive<RecyclerView>.scroll(): Observable<RecyclerViewScrollEvent> =
        Observable.create { emitter ->

            val listener = object : RecyclerView.OnScrollListener() {
                override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                    if (!emitter.isDisposed) {
                        emitter.onNext(RecyclerViewScrollEvent.ScrollStateChanged(recyclerView, newState))
                    }
                }

                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                    if (!emitter.isDisposed) {
                        emitter.onNext(RecyclerViewScrollEvent.Scrolled(recyclerView, dx, dy))
                    }
                }
            }

            item.addOnScrollListener(listener)

            emitter.setDisposable(AndroidMainThreadDisposable { item.removeOnScrollListener(listener) })
        }

fun Reactive<RecyclerView>.recycler(): Observable<RecyclerView.ViewHolder> =
        Observable.create { emitter ->
            item.setRecyclerListener { emitter.onNext(it) }

            emitter.setDisposable(AndroidMainThreadDisposable { item.setRecyclerListener(null) })
        }

sealed class ChildAttachStateChange {
    data class ChildViewDetachedFromWindow(val view: View) : ChildAttachStateChange()
    data class ChildViewAttachedToWindow(val view: View) : ChildAttachStateChange()
}

fun Reactive<RecyclerView>.childViewDetachedFromWindow() = childAttachStateChange().ofType<ChildAttachStateChange.ChildViewDetachedFromWindow>()

fun Reactive<RecyclerView>.childViewAttachedToWindow() = childAttachStateChange().ofType<ChildAttachStateChange.ChildViewAttachedToWindow>()

private fun Reactive<RecyclerView>.childAttachStateChange(): Observable<ChildAttachStateChange> =
        Observable.create { emitter ->

            val listener = object : RecyclerView.OnChildAttachStateChangeListener {
                override fun onChildViewDetachedFromWindow(view: View) {
                    if (!emitter.isDisposed) {
                        emitter.onNext(ChildAttachStateChange.ChildViewDetachedFromWindow(view))
                    }
                }

                override fun onChildViewAttachedToWindow(view: View) {
                    if (!emitter.isDisposed) {
                        emitter.onNext(ChildAttachStateChange.ChildViewAttachedToWindow(view))
                    }
                }
            }

            item.addOnChildAttachStateChangeListener(listener)

            emitter.setDisposable(AndroidMainThreadDisposable { item.removeOnChildAttachStateChangeListener(listener) })
        }

sealed class ItemTouch {
    data class TouchEvent(val recyclerView: RecyclerView, val motionEvent: MotionEvent) : ItemTouch()
    data class InterceptTouchEvent(val recyclerView: RecyclerView, val motionEvent: MotionEvent) : ItemTouch()
    data class RequestDisallowInterceptTouchEvent(val disallowIntercept: Boolean) : ItemTouch()
}

fun Reactive<RecyclerView>.touchEvent() = itemTouch().ofType<ItemTouch.TouchEvent>()

fun Reactive<RecyclerView>.interceptTouchEvent() = itemTouch().ofType<ItemTouch.InterceptTouchEvent>()

fun Reactive<RecyclerView>.requestDisallowInterceptTouchEvent() = itemTouch().ofType<ItemTouch.RequestDisallowInterceptTouchEvent>()

private fun Reactive<RecyclerView>.itemTouch(onInterceptTouchConsumed: Boolean = true): Observable<ItemTouch> =
        Observable.create { emitter ->

            val listener = object : RecyclerView.OnItemTouchListener {
                override fun onTouchEvent(rv: RecyclerView, e: MotionEvent) {
                    if (!emitter.isDisposed) {
                        emitter.onNext(ItemTouch.TouchEvent(rv, e))
                    }
                }

                override fun onInterceptTouchEvent(rv: RecyclerView, e: MotionEvent): Boolean {
                    if (!emitter.isDisposed) {
                        emitter.onNext(ItemTouch.InterceptTouchEvent(rv, e))
                    }
                    return onInterceptTouchConsumed
                }

                override fun onRequestDisallowInterceptTouchEvent(disallowIntercept: Boolean) {
                    if (!emitter.isDisposed) {
                        emitter.onNext(ItemTouch.RequestDisallowInterceptTouchEvent(disallowIntercept))
                    }
                }
            }

            item.addOnItemTouchListener(listener)

            emitter.setDisposable(AndroidMainThreadDisposable { item.removeOnItemTouchListener(listener) })
        }

sealed class AdapterData {
    object Changed : AdapterData()
    data class ItemRangeChanged(val positionStart: Int, val itemCount: Int) : AdapterData()
    data class ItemRangeInserted(val positionStart: Int, val itemCount: Int) : AdapterData()
    data class ItemRangeMoved(val fromPosition: Int, val toPosition: Int, val itemCount: Int) : AdapterData()
    data class ItemRangeRemoved(val positionStart: Int, val itemCount: Int) : AdapterData()
}

fun Reactive<RecyclerView>.changed() = adapterDataObserver().ofType<AdapterData.Changed>()

fun Reactive<RecyclerView>.itemRangeChanged() = adapterDataObserver().ofType<AdapterData.ItemRangeChanged>()

fun Reactive<RecyclerView>.itemRangeInserted() = adapterDataObserver().ofType<AdapterData.ItemRangeInserted>()

fun Reactive<RecyclerView>.itemRangeMoved() = adapterDataObserver().ofType<AdapterData.ItemRangeMoved>()

fun Reactive<RecyclerView>.itemRangeRemoved() = adapterDataObserver().ofType<AdapterData.ItemRangeRemoved>()

private fun Reactive<RecyclerView>.adapterDataObserver(): Observable<AdapterData> =
        Observable.create { emitter ->

            val listener = object : RecyclerView.AdapterDataObserver() {
                override fun onChanged() {
                    if (!emitter.isDisposed) {
                        emitter.onNext(AdapterData.Changed)
                    }
                }

                override fun onItemRangeChanged(positionStart: Int, itemCount: Int) {
                    if (!emitter.isDisposed) {
                        emitter.onNext(AdapterData.ItemRangeChanged(positionStart, itemCount))
                    }
                }

                override fun onItemRangeInserted(positionStart: Int, itemCount: Int) {
                    if (!emitter.isDisposed) {
                        emitter.onNext(AdapterData.ItemRangeInserted(positionStart, itemCount))
                    }
                }

                override fun onItemRangeMoved(fromPosition: Int, toPosition: Int, itemCount: Int) {
                    if (!emitter.isDisposed) {
                        emitter.onNext(AdapterData.ItemRangeMoved(fromPosition, toPosition, itemCount))
                    }
                }

                override fun onItemRangeRemoved(positionStart: Int, itemCount: Int) {
                    if (!emitter.isDisposed) {
                        emitter.onNext(AdapterData.ItemRangeRemoved(positionStart, itemCount))
                    }
                }
            }

            item.adapter?.registerAdapterDataObserver(listener)

            emitter.setDisposable(AndroidMainThreadDisposable { item.adapter?.unregisterAdapterDataObserver(listener) })
        }