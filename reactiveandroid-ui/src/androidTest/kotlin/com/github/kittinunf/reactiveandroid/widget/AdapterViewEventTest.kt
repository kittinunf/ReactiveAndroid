package com.github.kittinunf.reactiveandroid.widget

import android.support.test.InstrumentationRegistry
import android.support.test.rule.ActivityTestRule
import android.support.test.runner.AndroidJUnit4
import android.widget.AdapterView
import io.reactivex.observers.TestObserver
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.Matchers.equalTo
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class AdapterViewEventTest {

    @Rule
    @JvmField
    val activityRule = ActivityTestRule(AdapterViewTestActivity::class.java)

    val instrumentation = InstrumentationRegistry.getInstrumentation()

    @Test
    fun testItemSelected() {
        val spinner = activityRule.activity.spinner

        val t = TestObserver<ItemSelectedListener>()

        val s = spinner.rx_itemSelected().subscribeWith(t)

        t.assertNoValues()
        t.assertNoErrors()

        instrumentation.runOnMainSync {
            spinner.setSelection(1)
        }

        t.awaitCount(1)

        val first = t.values().first()
        assertThat(first.position, equalTo(1))
        assertThat(first.id, equalTo(1L))

        instrumentation.runOnMainSync {
            spinner.setSelection(3)
        }

        t.awaitCount(2)

        val second = t.values()[1]
        assertThat(second.position, equalTo(3))
        assertThat(second.id, equalTo(3L))

        s.dispose()

        instrumentation.runOnMainSync {
            spinner.setSelection(0)
        }

        t.awaitCount(2)
    }

    @Test
    fun testItemClick() {
        val listView = activityRule.activity.listView

        val t = TestObserver<ItemClickListener>()

        val s = listView.rx_itemClick().subscribeWith(t)

        t.assertNoValues()
        t.assertNoErrors()

        instrumentation.runOnMainSync {
            listView.performItemClick(listView.getChildAt(1), 2, 2)
        }

        t.awaitCount(1)
        val first = t.values().first()
        assertThat(first.position, equalTo(2))
        assertThat(first.id, equalTo(2L))

        instrumentation.runOnMainSync {
            listView.performItemClick(listView.getChildAt(3), 4, 4)
        }

        t.awaitCount(2)
        val second = t.values()[1]
        assertThat(second.position, equalTo(4))
        assertThat(second.id, equalTo(4L))

        s.dispose()

        instrumentation.runOnMainSync {
            listView.performItemClick(listView.getChildAt(4), 5, 5)
        }

        t.awaitCount(2)
    }

    @Test
    fun testNothingSelected() {
        val spinner = activityRule.activity.spinner

        val t1 = TestObserver<ItemSelectedListener>()
        val t2 = TestObserver<AdapterView<*>>()

        val s1 = spinner.rx_itemSelected().subscribeWith(t1)
        val s2 = spinner.rx_nothingSelected().subscribeWith(t2)

        t1.assertNoValues()
        t1.assertNoErrors()

        instrumentation.runOnMainSync {
            spinner.setSelection(1)
        }

        t1.awaitCount(1)

        instrumentation.runOnMainSync {
            activityRule.activity.clear()
        }

        t2.assertNoValues()
        t2.assertNoErrors()

        t2.awaitCount(1)
    }

}
