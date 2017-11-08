package com.github.kittinunf.reactiveandroid.support.v7.reactive.widget

import android.support.test.InstrumentationRegistry
import android.support.test.rule.ActivityTestRule
import android.support.test.runner.AndroidJUnit4
import android.support.v7.widget.RecyclerView
import com.github.kittinunf.reactiveandroid.support.v7.reactive.activity.RecyclerViewTestActivity
import org.hamcrest.CoreMatchers.equalTo
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class RecyclerViewTest {

    @Rule
    @JvmField
    val activityRule = ActivityTestRule(RecyclerViewTestActivity::class.java)

    val instrumentation = InstrumentationRegistry.getInstrumentation()

    lateinit var view: RecyclerView

    @Before
    fun before() {
        view = activityRule.activity.recyclerView
    }

    @Test
    fun scrollStateChanged() {

    }

    @Test
    fun scrolled() {
        val test = view.rx.scrolled().test()

        activityRule.activity.setItem1000Adapter()

        instrumentation.runOnMainSync {
            view.scrollBy(0, 100)
        }

        test.awaitCount(2)

        val values = test.values()
        val last = values.last()
        assertThat(last.dx, equalTo(0))
        assertThat(last.dy, equalTo(100))
    }

    @Test
    fun recycler() {

    }

    @Test
    fun childViewAttachedDetached() {
        val attach = view.rx.childViewAttachedToWindow().test()
        val detach = view.rx.childViewDetachedFromWindow().test()

        val child = activityRule.activity.child
        activityRule.activity.setItem1Adapter(child)

        attach.awaitCount(1)
        attach.assertValueCount(1)
        attach.assertValue(ChildAttachStateChange.ChildViewAttachedToWindow(child))

        activityRule.activity.unsetAdapter()
        detach.awaitCount(1)
        detach.assertValueCount(1)
        detach.assertValue(ChildAttachStateChange.ChildViewDetachedFromWindow(child))

        attach.dispose()
        detach.dispose()

        activityRule.activity.setItem1Adapter(child)
        attach.assertValueCount(1)
        detach.assertValueCount(1)
    }

    @Test
    fun touchEvent() {

    }

    @Test
    fun interceptTouchEvent() {

    }

    @Test
    fun changed() {

    }

    @Test
    fun itemChanged() {

    }

    @Test
    fun itemInserted() {

    }

    @Test
    fun itemMoved() {

    }

    @Test
    fun itemRemoved() {

    }
}