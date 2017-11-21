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
class DatePickerEventTest {

    @Rule
    @JvmField
    val activityRule = ActivityTestRule(DatePickerTestActivity::class.java)

    val instrumentation = InstrumentationRegistry.getInstrumentation()

    @Test
    @UiThreadTest
    @Ignore
    fun testChecked() {
        val picker = activityRule.activity.datePicker

        val t = TestObserver<DateChangedListener>()

        val s = picker.rx_dateChanged(DateState(2017, 1, 1)).subscribeWith(t)

        t.assertNoErrors()
        t.assertNoValues()

        picker.updateDate(2017, 1, 2)
        val first = t.values().first()

        // in Android - 18, onDateChangedListener will be called twice every time we update date
        t.assertValueCount(2) //t.assertValueCount(1)

        assertThat(first.year, equalTo(2017))
        assertThat(first.monthOfYear, equalTo(1))
        assertThat(first.dayOfMonth, equalTo(2))

        picker.updateDate(2018, 2, 10)
        val second = t.values().last()

        // in Android - 18, onDateChangedListener will be called twice every time we update date
        t.assertValueCount(4) //t.assertValueCount(4)

        assertThat(second.year, equalTo(2018))
        assertThat(second.monthOfYear, equalTo(2))
        assertThat(second.dayOfMonth, equalTo(10))

        s.dispose()

        // in Android - 18, onDateChangedListener will be called twice every time we update date
        t.assertValueCount(4) //t.assertValueCount(4)
    }
}
