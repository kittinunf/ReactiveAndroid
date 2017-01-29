package com.github.kittinunf.reactiveandroid.widget

import android.support.test.InstrumentationRegistry
import android.support.test.rule.ActivityTestRule
import android.support.test.runner.AndroidJUnit4
import android.widget.AdapterView
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.Matchers.equalTo
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import rx.observers.TestSubscriber
import java.util.concurrent.TimeUnit

@RunWith(AndroidJUnit4::class)
class AdapterViewEventTest {

    @Rule
    @JvmField
    val activityRule = ActivityTestRule(AdapterViewTestActivity::class.java)

    val instrumentation = InstrumentationRegistry.getInstrumentation()

    @Test
    fun testItemSelected() {
        val spinner = activityRule.activity.spinner

        val t = TestSubscriber<ItemSelectedListener>()

        val s = spinner.rx_itemSelected().subscribe(t)

        t.assertNoValues()
        t.assertNoErrors()

        instrumentation.runOnMainSync {
            spinner.setSelection(1)
        }

        assert(t.awaitValueCount(1, 3, TimeUnit.SECONDS))

        val first = t.onNextEvents.first()
        assertThat(first.position, equalTo(1))
        assertThat(first.id, equalTo(1L))

        instrumentation.runOnMainSync {
            spinner.setSelection(3)
        }

        assert(t.awaitValueCount(2, 3, TimeUnit.SECONDS))

        val second = t.onNextEvents[1]
        assertThat(second.position, equalTo(3))
        assertThat(second.id, equalTo(3L))

        s.unsubscribe()

        instrumentation.runOnMainSync {
            spinner.setSelection(0)
        }

        assert(t.awaitValueCount(2, 3, TimeUnit.SECONDS))
    }

    @Test
    fun testItemClick() {
        val listView = activityRule.activity.listView

        val t = TestSubscriber<ItemClickListener>()

        val s = listView.rx_itemClick().subscribe(t)

        t.assertNoValues()
        t.assertNoErrors()

        instrumentation.runOnMainSync {
            listView.performItemClick(listView.getChildAt(1), 2, 2)
        }

        assert(t.awaitValueCount(1, 3, TimeUnit.SECONDS))
        val first = t.onNextEvents.first()
        assertThat(first.position, equalTo(2))
        assertThat(first.id, equalTo(2L))

        instrumentation.runOnMainSync {
            listView.performItemClick(listView.getChildAt(3), 4, 4)
        }

        assert(t.awaitValueCount(2, 3, TimeUnit.SECONDS))
        val second = t.onNextEvents[1]
        assertThat(second.position, equalTo(4))
        assertThat(second.id, equalTo(4L))

        s.unsubscribe()

        instrumentation.runOnMainSync {
            listView.performItemClick(listView.getChildAt(4), 5, 5)
        }

        assert(t.awaitValueCount(2, 3, TimeUnit.SECONDS))
    }

    @Test
    fun testNothingSelected() {
        val spinner = activityRule.activity.spinner

        val t1 = TestSubscriber<ItemSelectedListener>()
        val t2 = TestSubscriber<AdapterView<*>>()

        val s1 = spinner.rx_itemSelected().subscribe(t1)
        val s2 = spinner.rx_nothingSelected().subscribe(t2)

        t1.assertNoValues()
        t1.assertNoErrors()

        instrumentation.runOnMainSync {
            spinner.setSelection(1)
        }

        assert(t1.awaitValueCount(1, 3, TimeUnit.SECONDS))

        instrumentation.runOnMainSync {
            activityRule.activity.clear()
        }

        t2.assertNoValues()
        t2.assertNoErrors()

        assert(t2.awaitValueCount(1, 3, TimeUnit.SECONDS))
    }

}