package com.github.kittinunf.reactiveandroid.widget

import android.support.test.InstrumentationRegistry
import android.support.test.annotation.UiThreadTest
import android.support.test.rule.ActivityTestRule
import android.support.test.runner.AndroidJUnit4
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
class TextViewPropertyTest {

    @Rule
    @JvmField
    val activityRule = ActivityTestRule(TextViewTestActivity::class.java)

    val instrumentation = InstrumentationRegistry.getInstrumentation()

    private val context = InstrumentationRegistry.getContext()

    companion object {
        @BeforeClass
        @JvmStatic
        fun setUp() {
            AndroidThreadScheduler.main = Schedulers.immediate()
        }
    }

    @Test
    @UiThreadTest
    fun testError() {
        val view = activityRule.activity.textView
        val error = view.rx_error

        Observable.just("error_text").bindTo(error)
        assertThat(view.error.toString(), equalTo("error_text"))

        error.bindTo(Observable.just("another_error_text") as Observable<CharSequence>)
        assertThat(view.error.toString(), equalTo("another_error_text"))
    }

    @Test
    @UiThreadTest
    fun testHint() {
        val view = activityRule.activity.textView
        val hint = view.rx_hint

        Observable.just("hint_text").bindTo(hint)
        assertThat(view.hint.toString(), equalTo("hint_text"))

        hint.bindTo(Observable.just("another_hint_text") as Observable<CharSequence>)
        assertThat(view.hint.toString(), equalTo("another_hint_text"))
    }

}