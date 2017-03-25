package com.github.kittinunf.reactiveandroid.widget

import android.support.test.InstrumentationRegistry
import android.support.test.annotation.UiThreadTest
import android.support.test.rule.UiThreadTestRule
import android.support.test.runner.AndroidJUnit4
import android.text.Editable
import android.widget.EditText
import android.widget.TextView
import org.hamcrest.CoreMatchers.equalTo
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import rx.observers.TestSubscriber

@RunWith(AndroidJUnit4::class)
class TextViewEventTest {
    @Rule
    @JvmField
    val uiThreadTestRule = UiThreadTestRule()

    private val context = InstrumentationRegistry.getContext()

    private val view = EditText(context)

    @Test
    @UiThreadTest
    fun testAfterTextChanged() {
        val t = TestSubscriber<Editable>()

        val s = view.rx_afterTextChanged().subscribe(t)

        t.assertNoErrors()
        t.assertNoValues()

        view.setText("hello", TextView.BufferType.NORMAL)
        val first = t.onNextEvents[0]
        assertThat(first.toString(), equalTo("hello"))
        t.assertValueCount(1)

        view.setText("world", TextView.BufferType.NORMAL)
        val second = t.onNextEvents[1]
        assertThat(second.toString(), equalTo("world"))
        t.assertValueCount(2)

        s.unsubscribe()
        view.setText("hello world", TextView.BufferType.NORMAL)
        t.assertValueCount(2)
    }

    @Test
    @UiThreadTest
    fun testBeforeTextChanged() {
        val t = TestSubscriber<BeforeTextChangedListener>()

        val s = view.rx_beforeTextChanged().subscribe(t)

        t.assertNoErrors()
        t.assertNoValues()

        view.setText("h", TextView.BufferType.NORMAL)
        val first = t.onNextEvents[0]
        assertThat(first.count, equalTo(0))
        assertThat(first.text.toString(), equalTo(""))
        t.assertValueCount(1)

        view.setText("he", TextView.BufferType.NORMAL)
        val second = t.onNextEvents[1]
        assertThat(second.count, equalTo(1))
        assertThat(second.text.toString(), equalTo("h"))
        t.assertValueCount(2)

        view.setText("hel", TextView.BufferType.NORMAL)
        val third = t.onNextEvents[2]
        assertThat(third.count, equalTo(2))
        assertThat(third.text.toString(), equalTo("he"))
        t.assertValueCount(3)

        s.unsubscribe()

        view.setText("hell", TextView.BufferType.NORMAL)
        t.assertValueCount(3)
    }

    @Test
    @UiThreadTest
    fun testTextChanged() {
        val t = TestSubscriber<TextChangedListener>()

        val s = view.rx_textChanged().subscribe(t)

        view.setText("h", TextView.BufferType.NORMAL)
        val first = t.onNextEvents[0]
        assertThat(first.text.toString(), equalTo("h"))
        t.assertValueCount(1)

        view.setText("he", TextView.BufferType.NORMAL)
        val second = t.onNextEvents[1]
        assertThat(second.text.toString(), equalTo("he"))
        t.assertValueCount(2)

        view.setText("hel", TextView.BufferType.NORMAL)
        val third = t.onNextEvents[2]
        assertThat(third.text.toString(), equalTo("hel"))
        t.assertValueCount(3)

        s.unsubscribe()

        view.setText("hell", TextView.BufferType.NORMAL)
        t.assertValueCount(3)
    }

}
