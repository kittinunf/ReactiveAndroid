package com.github.kittinunf.reactiveandroid

import com.github.kittinunf.reactiveandroid.rx.addTo
import com.github.kittinunf.reactiveandroid.rx.bindTo
import rx.Observable
import rx.subjects.PublishSubject
import rx.subjects.ReplaySubject
import rx.subscriptions.CompositeSubscription

class ActionErrorNotEnabled() : Throwable("Cannot execute Action is not enabled")

class Action<in T, U>(val enabledIf: Observable<Boolean>, val execution: (T) -> Observable<U>) {

    constructor(execution: (T) -> Observable<U>) : this(Observable.just(true), execution)

    private val _errors = PublishSubject.create<Throwable>()
    val errors = _errors.asObservable()

    private val _values = PublishSubject.create<U>()
    val values = _values.asObservable()

    private val _enabled = MutableProperty(false)
    val enabled = _enabled.observable

    private val _executing = MutableProperty(false)
    val executing = _executing.observable

    private val subscriptions = CompositeSubscription()

    init {
        _enabled.bindTo(Observable.combineLatest(enabledIf, executing, { enabled, executing ->
            enabled && !executing
        })).addTo(subscriptions)
    }

    fun execute(input: T): Observable<U> {

        val buffer = ReplaySubject.create<U>()

        var willBeExecuted = false

        if (_enabled.value) {
            _executing.value = true
            willBeExecuted = true
        }

        if (!willBeExecuted) {
            val error = ActionErrorNotEnabled()
            _errors.onNext(error)
            buffer.onError(error)
            return buffer
        }

        val workObservable = execution(input)
        val connectable = workObservable.publish()

        connectable.subscribe(buffer)

        buffer.subscribe({
            _values.onNext(it)
        }, {
            _errors.onNext(it)
            _executing.value = false
        }, {
            _executing.value = false
        }).addTo(subscriptions)

        connectable.connect()
        return buffer.asObservable()
    }

}