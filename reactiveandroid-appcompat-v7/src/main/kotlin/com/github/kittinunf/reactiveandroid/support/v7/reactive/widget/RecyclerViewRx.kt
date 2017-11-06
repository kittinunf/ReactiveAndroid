package com.github.kittinunf.reactiveandroid.support.v7.reactive.widget

import android.support.v7.widget.RecyclerView
import android.view.View
import com.github.kittinunf.reactiveandroid.FieldDelegate
import com.github.kittinunf.reactiveandroid.internal.AndroidMainThreadDisposable
import com.github.kittinunf.reactiveandroid.reactive.Reactive
import com.github.kittinunf.reactiveandroid.reactive.ofType
import io.reactivex.Observable

val RecyclerView.rx: Reactive<RecyclerView> by FieldDelegate { Reactive(it) }

sealed class RecyclerScrollEvent {
    data class ScrollStateChanged(val recyclerView: RecyclerView, val newState: Int) : RecyclerScrollEvent()
    data class Scrolled(val recyclerView: RecyclerView, val dx: Int, val dy: Int) : RecyclerScrollEvent()
}

fun Reactive<RecyclerView>.scrollStateChanged() = scroll().ofType<RecyclerScrollEvent.ScrollStateChanged>()

fun Reactive<RecyclerView>.scrolled() = scroll().ofType<RecyclerScrollEvent.Scrolled>()

private fun Reactive<RecyclerView>.scroll(): Observable<RecyclerScrollEvent> =
        Observable.create { emitter ->

            val listener = object : RecyclerView.OnScrollListener() {
                override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                    if (!emitter.isDisposed) {
                        emitter.onNext(RecyclerScrollEvent.ScrollStateChanged(recyclerView, newState))
                    }
                }

                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                    if (!emitter.isDisposed) {
                        emitter.onNext(RecyclerScrollEvent.Scrolled(recyclerView, dx, dy))
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

sealed class ChildAttachStateChanged {
    data class ChildAttachViewDetachedFromWindow(val view: View) : ChildAttachStateChanged()
    data class ChildAttachViewAttachedToWindow(val view: View) : ChildAttachStateChanged()
}

fun Reactive<RecyclerView>.childViewDetachedFromWindow() = childAttachStateChange().ofType<ChildAttachStateChanged.ChildAttachViewDetachedFromWindow>()

fun Reactive<RecyclerView>.childViewAttachedToWindow() = childAttachStateChange().ofType<ChildAttachStateChanged.ChildAttachViewAttachedToWindow>()

private fun Reactive<RecyclerView>.childAttachStateChange(): Observable<ChildAttachStateChanged> =
        Observable.create { emitter ->

            val listener = object : RecyclerView.OnChildAttachStateChangeListener {
                override fun onChildViewDetachedFromWindow(view: View) {
                    if (!emitter.isDisposed) {
                        emitter.onNext(ChildAttachStateChanged.ChildAttachViewDetachedFromWindow(view))
                    }
                }

                override fun onChildViewAttachedToWindow(view: View) {
                    if (!emitter.isDisposed) {
                        emitter.onNext(ChildAttachStateChanged.ChildAttachViewAttachedToWindow(view))
                    }
                }

            }

            item.addOnChildAttachStateChangeListener(listener)

            emitter.setDisposable(AndroidMainThreadDisposable { item.removeOnChildAttachStateChangeListener(listener) })
        }