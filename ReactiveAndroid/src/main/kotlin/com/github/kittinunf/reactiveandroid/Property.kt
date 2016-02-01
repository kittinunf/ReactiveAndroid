package com.github.kittinunf.reactiveandroid

import com.github.kittinunf.reactiveandroid.rx.plusAssign
import rx.Observable
import rx.Observer
import rx.Subscription
import rx.subjects.BehaviorSubject
import rx.subscriptions.CompositeSubscription

interface PropertyType<T> {

    val value: T

    val observable: Observable<T>

    fun subscribe(observer: Observer<T>) = observable.subscribe(observer)

    fun subscribe(onNext: (T) -> Unit) = observable.subscribe(onNext)

    fun subscribe(onNext: (T) -> Unit, onError: (Throwable) -> Unit) = observable.subscribe(onNext, onError)

    fun subscribe(onNext: (T) -> Unit, onError: (Throwable) -> Unit, onComplete: () -> Unit) = observable.subscribe(onNext, onError, onComplete)

}

interface MutablePropertyType<T> : PropertyType<T> {

    override var value: T

}

class ConstantProperty<T>(private val init: T) : PropertyType<T> {

    override val observable: Observable<T>
        get() = sink

    override val value: T = init
        get() = synchronized(this) { field }

    private val sink = BehaviorSubject.create(init)

}

class Property<T>(private val init: T) : PropertyType<T> {

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

    private val sink = BehaviorSubject.create(_value)

    private val subscriptions = CompositeSubscription()

    constructor(propertyType: PropertyType<T>) : this(propertyType.value) {
        subscriptions += propertyType.observable.subscribe({
            _value = it
        }, {
            throw UnsupportedOperationException("Property doesn't support onError")
        }, {
            subscriptions.unsubscribe()
        })
    }

}

class MutableProperty<T>(private val init: T) : MutablePropertyType<T> {

    override val observable: Observable<T>
        get() = sink

    override var value: T = init
        get() = synchronized(this) { field }
        set(value) {
            field = synchronized(this) {
                sink.onNext(value)
                value
            }
        }

    private val sink = BehaviorSubject.create(init)

    private val subscriptions = CompositeSubscription()

    fun bindTo(observable: Observable<T>) = modify(observable)

    fun bindTo(propertyType: PropertyType<T>) = modify(propertyType.observable)

    private fun modify(observable: Observable<T>): Subscription {
        subscriptions += observable.subscribe({
            value = it
        }, {
            throw UnsupportedOperationException("Property doesn't support onError")
        }, {
            subscriptions.unsubscribe()
        })
        return subscriptions
    }

}

