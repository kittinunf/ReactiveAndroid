package com.github.kittinunf.reactiveandroid

import com.github.kittinunf.reactiveandroid.rx.lift
import com.github.kittinunf.reactiveandroid.rx.subscribe
import org.junit.Assert.assertThat
import org.junit.Test
import rx.Observable
import org.hamcrest.CoreMatchers.`is` as isEqualTo

class ObservableExtensionTest {

    var result = ""

    fun liftTest(input: String) {
        result = input
    }

    class Foo {
        var value = 0

        fun plusWith(i: Int) {
            value += i
        }
    }

    @Test
    fun liftWithVariableMethod() {
        val f = Foo()

        Observable.range(0, 10).lift(f, Foo::plusWith)

        assertThat(f.value, isEqualTo(45))
    }

    @Test
    fun liftWithClassMethod() {
        Observable.just("Hello observable").lift(this, ObservableExtensionTest::liftTest)

        assertThat(result, isEqualTo("Hello observable"))
    }

    @Test
    fun subscribePair() {
        val firstPair = Pair(1, 2)
        val secondPair = Pair(8, 9)

        var sumFirst = 0
        var productSecond = 1
        Observable.concat(Observable.just(firstPair), Observable.just(secondPair)).subscribe({ f, s ->
            sumFirst += f
            productSecond *= s
        })

        assertThat(sumFirst, isEqualTo(9))
        assertThat(productSecond, isEqualTo(18))
    }

    @Test
    fun subscribeTriple() {
        var a = 0
        var b = 0
        var c =0

        Observable.just(Triple(1,2,3)).subscribe({ f, s, t ->
            a = f
            b = s
            c = t
        })

        assertThat(a, isEqualTo(1))
        assertThat(b, isEqualTo(2))
        assertThat(c, isEqualTo(3))
    }

}

 
