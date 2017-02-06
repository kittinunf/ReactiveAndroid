package com.github.kittinunf.reactiveandroid.widget

import android.support.test.InstrumentationRegistry
import android.support.test.annotation.UiThreadTest
import android.support.test.rule.UiThreadTestRule
import android.support.test.runner.AndroidJUnit4
import android.widget.CalendarView
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import rx.observers.TestSubscriber

@RunWith(AndroidJUnit4::class)
class CalendarViewEventTest {

    @Rule
    @JvmField
    val uiThreadTestRule = UiThreadTestRule()

    private val context = InstrumentationRegistry.getContext()
    private val view = CalendarView(context)

    @Test
    @UiThreadTest
    fun testDateChange() {
        val t = TestSubscriber<DateChangeListener>()
        view.rx_dateChange().subscribe(t)

        t.assertNoValues()
        t.assertNoErrors()
    }

}