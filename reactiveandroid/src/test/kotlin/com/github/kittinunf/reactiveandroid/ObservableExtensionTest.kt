package com.github.kittinunf.reactiveandroid

import com.github.kittinunf.reactiveandroid.reactive.cachedPrevious
import io.reactivex.Observable
import org.hamcrest.CoreMatchers.equalTo
import org.junit.Assert.assertThat
import org.junit.Test

class ObservableExtensionTest {

    @Test
    fun cachedPreviousWithInt() {
        val ints = Observable.just(1, 2, 3, 4)
        val test = ints.cachedPrevious().test()

        assertThat(test.valueCount(), equalTo(4))
        val values = test.values()
        assertThat(values[0].second, equalTo(1))
        assertThat(values[1] as Pair<Int, Int>, equalTo(1 to 2))
        assertThat(values[2] as Pair<Int, Int>, equalTo(2 to 3))
        assertThat(values[3] as Pair<Int, Int>, equalTo(3 to 4))
    }

    @Test
    fun cachedPreviousWithList() {
        val lists = Observable.just(emptyList(), listOf(1, 2, 3), listOf(1, 2, 3, 4, 5))

        val test = lists.cachedPrevious().test()

        assertThat(test.valueCount(), equalTo(3))

        val values = test.values()
        assertThat(values[0].second, equalTo(emptyList()))
        assertThat(values[1] as Pair<List<Int>, List<Int>>, equalTo(emptyList<Int>() to listOf(1, 2, 3)))
        assertThat(values[2] as Pair<List<Int>, List<Int>>, equalTo(listOf(1, 2, 3) to listOf(1, 2, 3, 4, 5)))
    }

    @Test
    fun cachedPreviousWithListOneItem() {
        val list = Observable.just(listOf(1,2,3))

        val test = list.cachedPrevious().test()

        assertThat(test.valueCount(), equalTo(1))
    }

}

 
