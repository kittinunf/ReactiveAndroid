package com.github.kittinunf.reactiveandroid

import com.github.kittinunf.reactiveandroid.rx.*
import org.junit.Assert.assertThat
import org.junit.Test
import rx.Observable
import rx.subjects.ReplaySubject
import org.hamcrest.CoreMatchers.`is` as isEqualTo

class ObservableExtensionTest {

    var result = ""

    fun liftTest(input: String) {
        result = input
    }

    fun liftTestError(e: Throwable) {
        result = e.message ?: ""
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

        Observable.range(0, 10).bindTo(f, Foo::plusWith)

        assertThat(f.value, isEqualTo(45))
    }

    @Test
    fun liftWithClassMethod() {
        Observable.just("Hello observable").bindTo(this, ObservableExtensionTest::liftTest)

        assertThat(result, isEqualTo("Hello observable"))
    }

    @Test
    fun bindNextWithVariableMethod() {
        Observable.just("hello observable bindNext").bindNext(this, ObservableExtensionTest::liftTest)

        assertThat(result, isEqualTo("hello observable bindNext"))
    }

    @Test
    fun bindErrorWithVariableMethod() {
        Observable.error<Int>(RuntimeException("error")).bindError(this, ObservableExtensionTest::liftTestError)

        assertThat(result, isEqualTo("error"))
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
        var c = 0

        Observable.just(Triple(1, 2, 3)).subscribe({ f, s, t ->
            a = f
            b = s
            c = t
        })

        assertThat(a, isEqualTo(1))
        assertThat(b, isEqualTo(2))
        assertThat(c, isEqualTo(3))
    }

    @Test
    fun cachedPrevious() {
        val subject = ReplaySubject.create<Pair<Int?, Int?>>()
        val ints = Observable.just(1, 2, 3, 4)
        ints.cachedPrevious().subscribe(subject)

        val v = subject.values
        assertThat(v[1] as Pair<Int, Int>, isEqualTo(1 to 2))
        assertThat(v[2] as Pair<Int, Int>, isEqualTo(2 to 3))
        assertThat(v[3] as Pair<Int, Int>, isEqualTo(3 to 4))
    }

}

 
