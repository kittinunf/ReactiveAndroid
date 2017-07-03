package com.github.kittinunf.reactiveandroid.widget

import android.support.test.InstrumentationRegistry
import android.support.test.annotation.UiThreadTest
import android.support.test.rule.ActivityTestRule
import android.support.test.runner.AndroidJUnit4
import com.github.kittinunf.reactiveandroid.reactive.bindTo
import com.github.kittinunf.reactiveandroid.scheduler.AndroidThreadScheduler
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers
import org.hamcrest.CoreMatchers.equalTo
import org.hamcrest.MatcherAssert.assertThat
import org.junit.BeforeClass
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import java.util.*

@RunWith(AndroidJUnit4::class)
class CalendarViewPropertyTest {

    @Rule
    @JvmField
    val activityRule = ActivityTestRule(CalendarViewTestActivity::class.java)

    val instrumentation = InstrumentationRegistry.getInstrumentation()

    private val context = InstrumentationRegistry.getContext()

    companion object {
        @BeforeClass
        @JvmStatic
        fun setUp() {
            AndroidThreadScheduler.main = Schedulers.trampoline()
        }
    }

    @Test
    @UiThreadTest
    fun testDate() {
        val calendarView = activityRule.activity.calendarView
        val date = calendarView.rx_date

        val cal = Calendar.getInstance()
        cal.set(2017, Calendar.JANUARY, 1, 0, 0)
        Observable.just(cal.timeInMillis).bindTo(date)

        assertThat(calendarView.date, equalTo(cal.timeInMillis))

        cal.set(2017, Calendar.DECEMBER, 31, 0, 0)
        date.bindTo(Observable.just(cal.timeInMillis))

        assertThat(calendarView.date, equalTo(cal.timeInMillis))
    }

}
