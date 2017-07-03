package com.github.kittinunf.reactiveandroid.view

import android.support.test.InstrumentationRegistry
import android.support.test.annotation.UiThreadTest
import android.support.test.rule.UiThreadTestRule
import android.support.test.runner.AndroidJUnit4
import android.view.View
import android.widget.LinearLayout
import io.reactivex.observers.TestObserver
import org.hamcrest.CoreMatchers.equalTo
import org.junit.Assert.assertThat
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class ViewGroupEventTest {

    @Rule
    @JvmField
    val uiThreadTestRule = UiThreadTestRule()

    private val context = InstrumentationRegistry.getContext()

    @Test
    @UiThreadTest
    fun testHierarchyChange() {
        val parent = LinearLayout(context)
        val child = View(context)

        val t1 = TestObserver<HierarchyChangeListener>()
        val t2 = TestObserver<HierarchyChangeListener>()

        val s1 = parent.rx_childViewAdded().subscribeWith(t1)
        val s2 = parent.rx_childViewRemoved().subscribeWith(t2)

        t1.assertNoValues()
        t1.assertNoErrors()
        t2.assertNoValues()
        t2.assertNoValues()

        parent.addView(child)

        t1.assertValueCount(1)
        t2.assertNoValues()
        val first = t1.values().first()
        assertThat(first.child, equalTo(child))
        assertThat(first.parent, equalTo(parent as View))

        parent.removeView(child)

        t1.assertValueCount(1)
        t2.assertValueCount(1)
        val anotherFirst = t2.values().first()
        assertThat(anotherFirst.child, equalTo(child))
        assertThat(anotherFirst.parent, equalTo(parent as View))

        s1.dispose()
        s2.dispose()

        parent.addView(child)
        parent.removeView(child)

        t1.assertValueCount(1)
        t2.assertValueCount(1)
    }

}
