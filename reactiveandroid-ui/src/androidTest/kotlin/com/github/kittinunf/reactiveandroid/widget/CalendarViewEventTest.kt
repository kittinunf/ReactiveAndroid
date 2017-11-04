package com.github.kittinunf.reactiveandroid.widget

import android.support.test.InstrumentationRegistry
import android.support.test.annotation.UiThreadTest
import android.support.test.rule.ActivityTestRule
import android.support.test.runner.AndroidJUnit4
import io.reactivex.observers.TestObserver
import org.hamcrest.CoreMatchers.equalTo
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Ignore
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class CalendarViewEventTest {

    @Rule
    @JvmField
    val activityRule = ActivityTestRule(CalendarViewTestActivity::class.java)

    val instrumentation = InstrumentationRegistry.getInstrumentation()

    @Test
    @UiThreadTest
    @Ignore
    fun testDateChange() {
        val calendarView = activityRule.activity.calendarView

        val t = TestObserver<DateChangeListener>()
        calendarView.rx_dateChange().subscribeWith(t)

        t.assertNoValues()
        t.assertNoErrors()

        t.assertValueCount(1)

        val first = t.values().first()
        assertThat(first.year, equalTo(2016))
    }

}
