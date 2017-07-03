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
class SeekBarEventTest {

    @Rule
    @JvmField
    val activityRule = ActivityTestRule(SeekBarTestActivity::class.java)

    val instrumentation = InstrumentationRegistry.getInstrumentation()

    val context = instrumentation.context

    @Test
    @UiThreadTest
    fun testProgressChanged() {
        val view = activityRule.activity.seekBar

        val t = TestObserver<SeekBarProgressChangeListener>()

        val s = view.rx_progressChanged().subscribeWith(t)

        t.assertNoValues()
        t.assertNoErrors()

        view.progress = 2

        val first = t.values()[0]
        assertThat(first.progress, equalTo(2))
        t.assertValueCount(1)

        view.progress = 4

        val second = t.values()[1]
        assertThat(second.progress, equalTo(4))
        t.assertValueCount(2)

        s.dispose()

        view.progress = 10
        t.assertValueCount(2)
    }

}
