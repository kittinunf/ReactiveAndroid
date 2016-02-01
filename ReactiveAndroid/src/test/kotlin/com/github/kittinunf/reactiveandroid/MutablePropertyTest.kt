package com.github.kittinunf.reactiveandroid

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

}
