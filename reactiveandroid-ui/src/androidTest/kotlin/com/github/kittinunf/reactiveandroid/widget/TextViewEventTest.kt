package com.github.kittinunf.reactiveandroid.widget

import android.support.test.InstrumentationRegistry
import android.support.test.annotation.UiThreadTest
import android.support.test.rule.ActivityTestRule
import android.support.test.runner.AndroidJUnit4
import android.text.Editable
import android.widget.TextView
import io.reactivex.observers.TestObserver
import org.hamcrest.CoreMatchers.equalTo
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class TextViewEventTest {
    @Rule
    @JvmField
    val activityRule = ActivityTestRule(TextViewTestActivity::class.java)

    val instrumentation = InstrumentationRegistry.getInstrumentation()

    @Test
    @UiThreadTest
    fun testAfterTextChanged() {
        val view = activityRule.activity.textView

        val t = TestObserver<Editable>()

        val s = view.rx_afterTextChanged().subscribeWith(t)

        t.assertNoErrors()
        t.assertNoValues()

        view.setText("hello", TextView.BufferType.NORMAL)
        val first = t.values()[0]
        assertThat(first.toString(), equalTo("hello"))
        t.assertValueCount(1)

        view.setText("world", TextView.BufferType.NORMAL)
        val second = t.values()[1]
        assertThat(second.toString(), equalTo("world"))
        t.assertValueCount(2)

        s.dispose()
        view.setText("hello world", TextView.BufferType.NORMAL)
        t.assertValueCount(2)
    }

    @Test
    @UiThreadTest
    fun testBeforeTextChanged() {
        val view = activityRule.activity.textView

        val t = TestObserver<BeforeTextChangedListener>()

        val s = view.rx_beforeTextChanged().subscribeWith(t)

        t.assertNoErrors()
        t.assertNoValues()

        view.setText("h", TextView.BufferType.NORMAL)
        val first = t.values()[0]
        assertThat(first.count, equalTo(0))
        assertThat(first.text.toString(), equalTo(""))
        t.assertValueCount(1)

        view.setText("he", TextView.BufferType.NORMAL)
        val second = t.values()[1]
        assertThat(second.count, equalTo(1))
        assertThat(second.text.toString(), equalTo("h"))
        t.assertValueCount(2)

        view.setText("hel", TextView.BufferType.NORMAL)
        val third = t.values()[2]
        assertThat(third.count, equalTo(2))
        assertThat(third.text.toString(), equalTo("he"))
        t.assertValueCount(3)

        s.dispose()

        view.setText("hell", TextView.BufferType.NORMAL)
        t.assertValueCount(3)
    }

    @Test
    @UiThreadTest
    fun testTextChanged() {
        val view = activityRule.activity.textView

        val t = TestObserver<TextChangedListener>()

        val s = view.rx_textChanged().subscribeWith(t)

        view.setText("h", TextView.BufferType.NORMAL)
        val first = t.values()[0]
        assertThat(first.text.toString(), equalTo("h"))
        t.assertValueCount(1)

        view.setText("he", TextView.BufferType.NORMAL)
        val second = t.values()[1]
        assertThat(second.text.toString(), equalTo("he"))
        t.assertValueCount(2)

        view.setText("hel", TextView.BufferType.NORMAL)
        val third = t.values()[2]
        assertThat(third.text.toString(), equalTo("hel"))
        t.assertValueCount(3)

        s.dispose()

        view.setText("hell", TextView.BufferType.NORMAL)
        t.assertValueCount(3)
    }

}
