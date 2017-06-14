package com.github.kittinunf.reactiveandroid.widget

import android.support.test.InstrumentationRegistry
import android.support.test.annotation.UiThreadTest
import android.support.test.rule.UiThreadTestRule
import android.support.test.runner.AndroidJUnit4
import android.widget.RadioButton
import android.widget.RadioGroup
import org.hamcrest.CoreMatchers.equalTo
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import rx.observers.TestSubscriber

@RunWith(AndroidJUnit4::class)
class RadioGroupEventTest {

    @Rule
    @JvmField
    val uiThreadTestRule = UiThreadTestRule()

    private val context = InstrumentationRegistry.getContext()

    private val view = RadioGroup(context)

    @Test
    @UiThreadTest
    fun testCheckedChange() {
        val radio1 = RadioButton(context)
        radio1.id = 1001

        val radio2 = RadioButton(context)
        radio2.id = 1002

        val radio3 = RadioButton(context)
        radio3.id = 1003

        view.addView(radio1)
        view.addView(radio2)
        view.addView(radio3)

        val t = TestSubscriber<RadioGroupCheckedChangeListener>()

        val s = view.rx_checkedChange().subscribe(t)

        t.assertNoValues()
        t.assertNoErrors()

        view.check(1003)
        val first = t.onNextEvents[0]
        assertThat(first.checkedId, equalTo(1003))
        t.assertValueCount(2)

        view.check(1002)
        val second = t.onNextEvents[3]
        assertThat(second.checkedId, equalTo(1002))
        t.assertValueCount(5)

        view.check(1001)
        val third = t.onNextEvents[6]
        assertThat(third.checkedId, equalTo(1001))
        t.assertValueCount(8)

        s.unsubscribe()

        view.check(1003)
        t.assertValueCount(8)
    }

}