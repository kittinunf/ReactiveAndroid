package com.github.kittinunf.reactiveandroid

import io.reactivex.Observable
import io.reactivex.Observer
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.subjects.BehaviorSubject

interface PropertyType<T> {

    val value: T?

    val observable: Observable<T>

    fun subscribe(observer: Observer<T>): Observer<T> = observable.subscribeWith(observer)

    fun subscribe(onNext: (T) -> Unit): Disposable = observable.subscribe(onNext)

    fun subscribe(onNext: (T) -> Unit, onError: (Throwable) -> Unit): Disposable = observable.subscribe(onNext, onError)

    fun subscribe(onNext: (T) -> Unit, onError: (Throwable) -> Unit, onComplete: () -> Unit): Disposable = observable.subscribe(onNext, onError, onComplete)

}

interface MutablePropertyType<T> : PropertyType<T> {

    override var value: T?

}

class Property<T>(init: T) : PropertyType<T> {

    override val observable: Observable<T>
        get() = sink

    private var _value: T = init
        set(value) {
            field = synchronized(this) {
                sink.onNext(value)
                value
            }
        }

    override val value: T = _value
        get() = synchronized(this) {
            field = _value
            field
        }

    private val sink = BehaviorSubject.createDefault<T>(init)

    private val compositeDisposable = CompositeDisposable()

    constructor(propertyType: PropertyType<T>) : this(propertyType.value!!) {
        compositeDisposable.add(propertyType.observable.subscribe({
            _value = it
        }, {
            throw UnsupportedOperationException("Property doesn't support onError")
        }, {
            compositeDisposable.dispose()
        }))
    }

}

class MutableProperty<T>(init: T?) : MutablePropertyType<T> {

    constructor() : this(null)

    override val observable: Observable<T>
        get() = sink.filter { it != null }

    override var value: T? = init
        get() = synchronized(this) { field }
        set(value) {
            field = synchronized(this) {
                if (value != null) sink.onNext(value)
                value
            }
        }

    private val sink = if (init == null) BehaviorSubject.create<T>() else BehaviorSubject.createDefault(init)

    fun bindTo(observable: Observable<T>) = bind(observable)

    fun bindTo(propertyType: PropertyType<T>) = bind(propertyType.observable)

    private fun bind(observable: Observable<T>): Disposable {
        return observable.subscribe({
            value = it
        }, {
            throw UnsupportedOperationException("Property doesn't support onError")
        }, {
            //do nothing
        })
    }

}
