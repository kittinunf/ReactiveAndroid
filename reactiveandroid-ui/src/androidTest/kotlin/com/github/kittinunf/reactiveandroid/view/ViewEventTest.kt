package com.github.kittinunf.reactiveandroid.view

import android.support.test.InstrumentationRegistry
import android.support.test.annotation.UiThreadTest
import android.support.test.rule.UiThreadTestRule
import android.support.test.runner.AndroidJUnit4
import android.view.View
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import rx.observers.TestSubscriber

@RunWith(AndroidJUnit4::class)
class ViewEventTest {

    @Rule @JvmField val uiThreadTestRule = UiThreadTestRule()

    private val context = InstrumentationRegistry.getContext()
    private val view = View(context)

    @Test
    @UiThreadTest
    fun testClick() {
        val t = TestSubscriber<View>()
        val subscription = view.rx_click().subscribe(t)

        t.assertNoErrors()
        t.assertNoValues()

        view.performClick()
        t.assertValueCount(1)

        view.performClick()
        t.assertValueCount(2)

        subscription.unsubscribe()

        view.performClick()
        t.assertValueCount(2)
    }

}
