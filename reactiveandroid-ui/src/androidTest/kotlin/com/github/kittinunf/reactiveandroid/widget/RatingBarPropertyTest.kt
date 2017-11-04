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

@RunWith(AndroidJUnit4::class)
class RatingBarPropertyTest {

    @Rule
    @JvmField
    val activityRule = ActivityTestRule(RatingBarTestActivity::class.java)

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
    fun testNumStars() {
        val ratingBar = activityRule.activity.ratingBar
        val numStars = ratingBar.rx_numStars

        Observable.just(10).bindTo(numStars)
        assertThat(ratingBar.numStars, equalTo(10))

        numStars.bindTo(Observable.just(5))
        assertThat(ratingBar.numStars, equalTo(5))
    }

    @Test
    @UiThreadTest
    fun testRating() {
        val ratingBar = activityRule.activity.ratingBar
        val rating = ratingBar.rx_rating

        Observable.just(1.0f).bindTo(rating)
        assertThat(ratingBar.rating, equalTo(1.0f))

        rating.bindTo(Observable.just(5.0f))
        assertThat(ratingBar.rating, equalTo(5.0f))
    }

    @Test
    @UiThreadTest
    fun testStepSize() {
        val ratingBar = activityRule.activity.ratingBar
        val steps = ratingBar.rx_stepSize

        Observable.just(1.0f).bindTo(steps)
        assertThat(ratingBar.stepSize, equalTo(1.0f))

        steps.bindTo(Observable.just(0.5f))
        assertThat(ratingBar.stepSize, equalTo(0.5f))
    }

}
