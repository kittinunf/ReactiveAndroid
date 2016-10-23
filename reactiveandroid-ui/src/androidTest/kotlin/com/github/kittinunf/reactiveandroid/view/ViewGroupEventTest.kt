package com.github.kittinunf.reactiveandroid.view

import android.support.test.InstrumentationRegistry
import android.support.test.annotation.UiThreadTest
import android.support.test.rule.UiThreadTestRule
import android.support.test.runner.AndroidJUnit4
import android.view.View
import android.widget.LinearLayout
import org.hamcrest.CoreMatchers.equalTo
import org.junit.Assert.assertThat
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import rx.observers.TestSubscriber

@RunWith(AndroidJUnit4::class)
class ViewGroupEventTest {

    @Rule @JvmField
    val uiThreadTestRule = UiThreadTestRule()

    private val context = InstrumentationRegistry.getContext()

    @Test
    @UiThreadTest
    fun testHierarchyChange() {
        val parent = LinearLayout(context)
        val child = View(context)

        val t1 = TestSubscriber<HierarchyChangeListener>()
        val t2 = TestSubscriber<HierarchyChangeListener>()

        val s1 = parent.rx_childViewAdded().subscribe(t1)
        val s2 = parent.rx_childViewRemoved().subscribe(t2)

        t1.assertNoValues()
        t1.assertNoErrors()
        t2.assertNoValues()
        t2.assertNoValues()

        parent.addView(child)

        t1.assertValueCount(1)
        t2.assertNoValues()
        val first = t1.onNextEvents.first()
        assertThat(first.child, equalTo(child))
        assertThat(first.parent, equalTo(parent as View))

        parent.removeView(child)

        t1.assertValueCount(1)
        t2.assertValueCount(1)
        val anotherFirst = t2.onNextEvents.first()
        assertThat(anotherFirst.child, equalTo(child))
        assertThat(anotherFirst.parent, equalTo(parent as View))

        s1.unsubscribe()
        s2.unsubscribe()

        parent.addView(child)
        parent.removeView(child)

        t1.assertValueCount(1)
        t2.assertValueCount(1)
    }

}
