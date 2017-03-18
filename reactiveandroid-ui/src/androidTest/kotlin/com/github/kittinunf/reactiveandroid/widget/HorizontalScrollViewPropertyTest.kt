package com.github.kittinunf.reactiveandroid.widget

import android.support.test.InstrumentationRegistry
import android.support.test.annotation.UiThreadTest
import android.support.test.rule.UiThreadTestRule
import android.support.test.runner.AndroidJUnit4
import android.widget.HorizontalScrollView
import com.github.kittinunf.reactiveandroid.rx.bindTo
import com.github.kittinunf.reactiveandroid.scheduler.AndroidThreadScheduler
import org.hamcrest.CoreMatchers.equalTo
import org.hamcrest.MatcherAssert.assertThat
import org.junit.BeforeClass
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import rx.Observable
import rx.schedulers.Schedulers

@RunWith(AndroidJUnit4::class)
class HorizontalScrollViewPropertyTest {

    @Rule
    @JvmField
    val uiThreadTestRule = UiThreadTestRule()

    private val context = InstrumentationRegistry.getContext()
    private val instrument = InstrumentationRegistry.getInstrumentation()
    private val view = HorizontalScrollView(context)

    companion object {
        @BeforeClass
        @JvmStatic
        fun setUp() {
            AndroidThreadScheduler.main = Schedulers.immediate()
        }
    }

    @Test
    @UiThreadTest
    fun testSmoothScrollEnabled() {
        val smoothScrollEnabled = view.rx_smoothScrollingEnabled

        Observable.just(false).bindTo(smoothScrollEnabled)
        assertThat(view.isSmoothScrollingEnabled, equalTo(false))

        view.rx_smoothScrollingEnabled.bindTo(Observable.just(true))
        assertThat(view.isSmoothScrollingEnabled, equalTo(true))
    }

}