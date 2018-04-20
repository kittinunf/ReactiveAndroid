package com.github.kittinunf.reactiveandroid

import com.github.kittinunf.reactiveandroid.reactive.cachedPrevious
import io.reactivex.Flowable
import org.hamcrest.CoreMatchers
import org.junit.Assert
import org.junit.Test

class FlowableExtension {

    @Test
    fun cachedPreviousWithInt() {
        val ints = Flowable.just(1, 2, 3, 4)
        val test = ints.cachedPrevious().test()

        Assert.assertThat(test.valueCount(), CoreMatchers.equalTo(4))
        val values = test.values()
        Assert.assertThat(values[0].second, CoreMatchers.equalTo(1))
        Assert.assertThat(values[1] as Pair<Int, Int>, CoreMatchers.equalTo(1 to 2))
        Assert.assertThat(values[2] as Pair<Int, Int>, CoreMatchers.equalTo(2 to 3))
        Assert.assertThat(values[3] as Pair<Int, Int>, CoreMatchers.equalTo(3 to 4))
    }

    @Test
    fun cachedPreviousWithList() {
        val lists = Flowable.just(emptyList(), listOf(1, 2, 3), listOf(1, 2, 3, 4, 5))

        val test = lists.cachedPrevious().test()

        Assert.assertThat(test.valueCount(), CoreMatchers.equalTo(3))

        val values = test.values()
        Assert.assertThat(values[0].second, CoreMatchers.equalTo(emptyList()))
        Assert.assertThat(values[1] as Pair<List<Int>, List<Int>>, CoreMatchers.equalTo(emptyList<Int>() to listOf(1, 2, 3)))
        Assert.assertThat(values[2] as Pair<List<Int>, List<Int>>, CoreMatchers.equalTo(listOf(1, 2, 3) to listOf(1, 2, 3, 4, 5)))
    }

    @Test
    fun cachedPreviousWithListOneItem() {
        val list = Flowable.just(listOf(1, 2, 3))

        val test = list.cachedPrevious().test()

        Assert.assertThat(test.valueCount(), CoreMatchers.equalTo(1))
    }
}