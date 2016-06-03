package com.github.kittinunf.reactiveandroid

import org.hamcrest.CoreMatchers.nullValue
import org.junit.Assert.assertThat
import org.junit.Test
import rx.Observable
import org.hamcrest.CoreMatchers.`is` as isEqualTo

class MutablePropertyTest {

    @Test
    fun mutablePropertySubscribing() {
        val mp = MutableProperty(42)

        var result = 0
        mp.subscribe {
            result = it
        }

        assertThat(result, isEqualTo(42))
    }

    @Test
    fun mutablePropertyCanChangeValue() {
        val mp = MutableProperty(true)

        mp.value = false

        var result = true
        mp.subscribe {
            result = it
        }

        assertThat(result, isEqualTo(false))
    }

    @Test
    fun mutablePropertyBindTo() {
        val mp = MutableProperty("hello")

        val o = Observable.just("world")
        mp.bindTo(o)

        assertThat(mp.value, isEqualTo("world"))
    }

    @Test
    fun mutablePropertyBindToAnotherProperty() {
        val mp1 = MutableProperty('a')
        val mp2 = MutableProperty('z')

        mp2.bindTo(mp1)

        mp1.value = 'b'
        mp1.value = 'c'
        mp1.value = 'd'

        assertThat(mp2.value, isEqualTo('d'))
    }

    @Test
    fun mutablePropertyWithoutInitialValue() {
        val mp = MutableProperty<Int>()

        assertThat(mp.value, nullValue())
    }

    @Test
    fun mutablePropertyWithoutInitialValueSubscribing() {
        val mp = MutableProperty<Int>()

        var called = false
        mp.subscribe {
            called = true
        }

        assertThat(called, isEqualTo(false))
    }

    @Test
    fun mutablePropertyWithoutInitialValueCanChangeValue() {
        val mp = MutableProperty<String>()

        var text = ""
        mp.subscribe {
            text = it
        }

        mp.value = "hello world!!"
        assertThat(text, isEqualTo("hello world!!"))
    }

    @Test
    fun mutablePropertyWithoutInitialValueBindTo() {
        val mp = MutableProperty<Int>()
        val o = Observable.just(68)

        mp.bindTo(o)
        assertThat(mp.value, isEqualTo(68))
    }

}
