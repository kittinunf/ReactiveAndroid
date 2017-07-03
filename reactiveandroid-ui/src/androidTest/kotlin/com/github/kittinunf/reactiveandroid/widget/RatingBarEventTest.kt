package com.github.kittinunf.reactiveandroid.widget

import android.support.test.InstrumentationRegistry
import android.support.test.annotation.UiThreadTest
import android.support.test.rule.ActivityTestRule
import android.support.test.runner.AndroidJUnit4
import io.reactivex.observers.TestObserver
import org.hamcrest.CoreMatchers.equalTo
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class RatingBarEventTest {

    @Rule
    @JvmField
    val activityRule = ActivityTestRule(RatingBarTestActivity::class.java)

    val instrumentation = InstrumentationRegistry.getInstrumentation()

    @Test
    @UiThreadTest
    fun testRatingBarChange() {
        val view = activityRule.activity.ratingBar

        val t = TestObserver<RatingBarChangeListener>()

        val s = view.rx_ratingBarChange().subscribeWith(t)

        t.assertNoValues()
        t.assertNoErrors()

        view.rating = 2.0f
        val first = t.values()[0]
        assertThat(first.rating, equalTo(2.0f))
        t.assertValueCount(1)

        view.rating = 3.0f
        val second = t.values()[1]
        assertThat(second.rating, equalTo(3.0f))
        t.assertValueCount(2)

        s.dispose()

        view.rating = 1.0f
        t.assertValueCount(2)
    }

}
