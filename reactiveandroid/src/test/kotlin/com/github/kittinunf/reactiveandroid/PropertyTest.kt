package com.github.kittinunf.reactiveandroid

import org.junit.Assert.assertThat
import org.junit.Test
import org.hamcrest.CoreMatchers.`is` as isEqualTo

class PropertyTest {

    @Test
    fun propertySubscribing() {
        val p = Property(42)

        var result = 0
        p.subscribe {
            result = it
        }

        assertThat(result, isEqualTo(42))
    }

    @Test
    fun propertySubscribingAll() {
        val p = Property(listOf(1.1, 2.2, 3.3))

        var count = 0
        p.subscribe(onNext = {
            print(count)
            it.forEach { count++ }
        }, onError = {
            //this supposed to not being called
        }, onComplete = {
            //this supposed to not being called
        })

        assertThat(count, isEqualTo(3))
    }

    @Test
    fun propertyWithExternalProperty() {
        val p1 = Property(true)
        val p2 = Property(p1)

        //p1.value = false //not compiled

        assertThat(p2.value, isEqualTo(true))
    }

    @Test
    fun propertyChangedWithExternalProperty() {
        val p1 = MutableProperty(18)
        val p2 = Property(p1)

        p1.value = 42

        assertThat(p2.value, isEqualTo(42))
    }

    @Test
    fun constantPropertySubscribing() {
        val cp = ConstantProperty("string")

        var result = ""
        cp.subscribe { result = it  }

        //cp.value = 4 //not compiled

        assertThat(result, isEqualTo("string"))
    }

}