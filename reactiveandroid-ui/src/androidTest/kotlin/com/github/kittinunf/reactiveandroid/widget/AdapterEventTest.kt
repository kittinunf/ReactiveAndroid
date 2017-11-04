package com.github.kittinunf.reactiveandroid.widget

import android.support.test.InstrumentationRegistry
import android.support.test.annotation.UiThreadTest
import android.support.test.rule.UiThreadTestRule
import android.support.test.runner.AndroidJUnit4
import android.widget.ArrayAdapter
import io.reactivex.observers.TestObserver
import org.junit.BeforeClass
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class AdapterEventTest {

    @Rule
    @JvmField
    val uiThreadTestRule = UiThreadTestRule()

    private val context = InstrumentationRegistry.getContext()

    companion object {
        @BeforeClass
        @JvmStatic
        fun setUp() {
        }
    }

    @Test
    @UiThreadTest
    fun testAdapterChanged() {
        val adapter = ArrayAdapter(context, android.R.layout.simple_list_item_1, mutableListOf("a", "b", "c", "d", "e"))

        val t = TestObserver<Unit>()
        val s = adapter.rx_changed().subscribeWith(t)

        t.assertNoValues()
        t.assertNoErrors()

        adapter.add("1")
        t.assertValueCount(1)

        adapter.add("2")
        t.assertValueCount(2)

        s.dispose()

        adapter.add("3")
        t.assertValueCount(2)
    }

    @Test
    @UiThreadTest
    fun testAdapterInvalidated() {
        val adapter = ArrayAdapter(context, android.R.layout.simple_list_item_1, mutableListOf("1", "2", "3", "4"))

        val t = TestObserver<Unit>()
        val s = adapter.rx_invalidated().subscribeWith(t)

        t.assertNoValues()
        t.assertNoErrors()

        adapter.filter.filter("1")
        t.assertNoValues()

        adapter.filter.filter("2")
        t.assertNoValues()

        adapter.filter.filter("a")
        t.awaitCount(1)

        s.dispose()

        adapter.filter.filter("b")
        t.awaitCount(1)
    }

}
