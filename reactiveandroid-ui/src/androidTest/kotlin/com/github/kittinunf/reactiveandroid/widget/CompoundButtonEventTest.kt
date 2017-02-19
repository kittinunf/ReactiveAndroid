package com.github.kittinunf.reactiveandroid.widget

import android.support.test.InstrumentationRegistry
import android.support.test.annotation.UiThreadTest
import android.support.test.rule.UiThreadTestRule
import android.support.test.runner.AndroidJUnit4
import android.widget.CheckBox
import org.hamcrest.CoreMatchers.equalTo
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import rx.observers.TestSubscriber

@RunWith(AndroidJUnit4::class)
class CompoundButtonEventTest {

    @Rule
    @JvmField
    val uiThreadTestRule = UiThreadTestRule()

    private val context = InstrumentationRegistry.getContext()
    private val view = CheckBox(context)

    @Test
    @UiThreadTest
    fun testChecked() {
        val t = TestSubscriber<CompoundButtonCheckedChangeListener>()

        val s = view.rx_checkedChange().subscribe(t)

        t.assertNoErrors()
        t.assertNoValues()

        view.isChecked = true
        t.assertValueCount(1)
        val first = t.onNextEvents.first()
        assertThat(first.isChecked, equalTo(true))
        assertThat(first.compoundButton == view, equalTo(true))

        view.isChecked = false
        t.assertValueCount(2)

        s.unsubscribe()

        view.isChecked = true
        t.assertValueCount(2)
    }
}