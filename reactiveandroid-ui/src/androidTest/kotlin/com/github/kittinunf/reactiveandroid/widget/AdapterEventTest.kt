package com.github.kittinunf.reactiveandroid.widget

import android.support.test.InstrumentationRegistry
import android.support.test.annotation.UiThreadTest
import android.support.test.rule.UiThreadTestRule
import android.support.test.runner.AndroidJUnit4
import android.widget.ArrayAdapter
import org.junit.BeforeClass
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import rx.observers.TestSubscriber
import java.util.concurrent.TimeUnit

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

        val t = TestSubscriber<Unit>()
        val s = adapter.rx_changed().subscribe(t)

        t.assertNoValues()
        t.assertNoErrors()

        adapter.add("1")
        t.assertValueCount(1)

        adapter.add("2")
        t.assertValueCount(2)

        s.unsubscribe()

        adapter.add("3")
        t.assertValueCount(2)
    }

    @Test
    @UiThreadTest
    fun testAdapterInvalidated() {
        val adapter = ArrayAdapter(context, android.R.layout.simple_list_item_1, mutableListOf("1", "2", "3", "4"))

        val t = TestSubscriber<Unit>()
        val s = adapter.rx_invalidated().subscribe(t)

        t.assertNoValues()
        t.assertNoErrors()

        adapter.filter.filter("1")
        t.assertNoValues()

        adapter.filter.filter("2")
        t.assertNoValues()

        adapter.filter.filter("a")
        t.awaitValueCount(1, 2, TimeUnit.SECONDS)

        s.unsubscribe()

        adapter.filter.filter("b")
        t.awaitValueCount(1, 2, TimeUnit.SECONDS)
    }

}
