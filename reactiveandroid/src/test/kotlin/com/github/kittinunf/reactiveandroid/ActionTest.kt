package com.github.kittinunf.reactiveandroid

import com.github.kittinunf.reactiveandroid.rx.bindTo
import org.hamcrest.CoreMatchers.isA
import org.junit.Assert.assertThat
import org.junit.Test
import rx.Observable
import rx.subjects.PublishSubject
import org.hamcrest.CoreMatchers.`is` as isEqualTo

class ActionTest {

    val _enabled = MutableProperty(false)

    val completeTestSubject = PublishSubject.create<Unit>()

    val evenNumberAction = Action(_enabled.observable) { i: Int ->
        Observable.create<String> { s ->
            if (i % 2 == 0) {
                s.onNext("$i")
                s.onNext("$i$i")
                completeTestSubject.subscribe {
                    s.onCompleted()
                }
            } else {
                s.onError(RuntimeException("Does not accept odd values"))
            }
        }
    }

    @Test
    fun isNotExecutableWhenCreated() {
        val executing = MutableProperty(true)
        executing.bindTo(evenNumberAction.executing)
        assertThat(executing.value, isEqualTo(false))
    }

    @Test
    fun isNotEnabledWhenCreated() {
        val enabled = MutableProperty(true)
        enabled.bindTo(evenNumberAction.enabled)
        assertThat(enabled.value, isEqualTo(false))
    }

    @Test
    fun isEnableAndDisableBasedOnEnableIfProperty() {
        val enabled = MutableProperty(true)
        enabled.bindTo(evenNumberAction.enabled)

        _enabled.value = true
        assertThat(enabled.value, isEqualTo(true))

        _enabled.value = false
        assertThat(enabled.value, isEqualTo(false))
    }

    @Test
    fun isNotExecutingWhenActionNotYetExecuted() {
        val executing = MutableProperty(true)
        executing.bindTo(evenNumberAction.executing)

        _enabled.value = true
        assertThat(executing.value, isEqualTo(false))

        _enabled.value = false
        assertThat(executing.value, isEqualTo(false))
    }

    @Test
    fun throwsErrorWhenExecuteUnexecutableAction() {
        val error = MutableProperty(Throwable())
        error.bindTo(evenNumberAction.errors)

        evenNumberAction.execute(5)
        assertThat(error.value as? ActionErrorNotEnabled, isA(ActionErrorNotEnabled::class.java))
    }

    @Test
    fun isExecutingBasedOnWhetherObservableCompleted() {
        val executing = MutableProperty(false)
        executing.bindTo(evenNumberAction.executing)

        _enabled.value = true
        evenNumberAction.execute(6)

        assertThat(executing.value, isEqualTo(true))

        completeTestSubject.onNext(Unit)
        assertThat(executing.value, isEqualTo(false))
    }

    @Test
    fun isNotEnabledWhileExecuting() {
        val enabled = MutableProperty(false)
        enabled.bindTo(evenNumberAction.enabled)

        _enabled.value = true
        evenNumberAction.execute(6)

        assertThat(enabled.value, isEqualTo(false))

        completeTestSubject.onNext(Unit)
        assertThat(enabled.value, isEqualTo(true))
    }

    @Test
    fun receivesValuesWhileExecutedEndedWithSuccess() {
        val values = MutableProperty("")
        values.bindTo(evenNumberAction.values)

        val enabled = MutableProperty(false)
        enabled.bindTo(evenNumberAction.enabled)

        val allValues = MutableProperty(mutableListOf<String>())
        evenNumberAction.values.scan(mutableListOf<String>()) { acc, i ->
            acc.add(i)
            acc
        }.bindTo(allValues)

        _enabled.value = true
        evenNumberAction.execute(8)
        completeTestSubject.onNext(Unit)

        assertThat(values.value, isEqualTo("88"))
        assertThat(allValues.value, isEqualTo(listOf("8", "88")))
    }

    @Test
    fun receivesValuesWhileExecutedEndedWithSuccessMultipleTimes() {
        val values = MutableProperty("")
        values.bindTo(evenNumberAction.values)

        val allValues = MutableProperty(mutableListOf<String>())
        evenNumberAction.values.scan(mutableListOf<String>()) { acc, i ->
            acc.add(i)
            acc
        }.bindTo(allValues)

        _enabled.value = true
        evenNumberAction.execute(10)
        completeTestSubject.onNext(Unit)

        evenNumberAction.execute(12)
        completeTestSubject.onNext(Unit)

        assertThat(allValues.value, isEqualTo(listOf("10", "1010", "12", "1212")))
    }

    @Test
    fun receivesErrorsWhenExecutionEndedWithFailure() {
        val errors = MutableProperty(Throwable())
        errors.bindTo(evenNumberAction.errors)

        _enabled.value = true
        evenNumberAction.execute(5)

        assertThat(errors.value as? RuntimeException, isA(RuntimeException::class.java))
    }


}