package com.github.kittinunf.reactiveandroid.widget

import android.support.test.InstrumentationRegistry
import android.support.test.espresso.Espresso.onData
import android.support.test.espresso.Espresso.onView
import android.support.test.espresso.action.ViewActions.clearText
import android.support.test.espresso.action.ViewActions.click
import android.support.test.espresso.action.ViewActions.typeText
import android.support.test.espresso.matcher.RootMatchers.withDecorView
import android.support.test.espresso.matcher.ViewMatchers.withId
import android.support.test.rule.ActivityTestRule
import android.support.test.runner.AndroidJUnit4
import org.hamcrest.CoreMatchers
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.CoreMatchers.equalTo
import org.hamcrest.CoreMatchers.not
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import rx.observers.TestSubscriber

@RunWith(AndroidJUnit4::class)
class AutoCompleteTextViewEventTest {

    @Rule
    @JvmField
    val activityRule = ActivityTestRule(AutoCompleteTextViewTestActivity::class.java)

    val instrumentation = InstrumentationRegistry.getInstrumentation()

    @Test
    fun testItemClick() {
        val autoCompleteTextView = activityRule.activity.textView

        val t = TestSubscriber<ItemClickListener>()

        val s = autoCompleteTextView.rx_itemClick().subscribe(t)

        t.assertNoErrors()
        t.assertNoValues()

        onView(withId(android.R.id.input))
                .perform(typeText("tw"))

        onData(CoreMatchers.startsWith("two"))
                .inRoot(withDecorView(not(`is`(activityRule.activity.window.decorView))))
                .perform(click())

        t.assertValueCount(1)

        val first = t.onNextEvents.first()
        assertThat(first.position, equalTo(0))
        assertThat(first.id, equalTo(0L))

        onView(withId(android.R.id.input))
                .perform(clearText(), typeText("th"))

        onData(CoreMatchers.startsWith("forty three"))
                .inRoot(withDecorView(not(`is`(activityRule.activity.window.decorView))))
                .perform(click())

        t.assertValueCount(2)

        val second = t.onNextEvents[1]
        assertThat(second.position, equalTo(2))
        assertThat(second.id, equalTo(2L))

        s.unsubscribe()

        onView(withId(android.R.id.input))
                .perform(clearText(), typeText("tw"))

        onData(CoreMatchers.startsWith("twenty one"))
                .inRoot(withDecorView(not(`is`(activityRule.activity.window.decorView))))
                .perform(click())

        t.assertValueCount(2)
    }

    @Test
    fun testDismiss() {
        val autoCompleteTextView = activityRule.activity.textView

        val t = TestSubscriber<Unit>()

        val s = autoCompleteTextView.rx_dismiss().subscribe(t)

        t.assertNoErrors()
        t.assertNoValues()

        onView(withId(android.R.id.input))
                .perform(clearText(), typeText("th"), clearText())

        t.assertValueCount(1)

        onView(withId(android.R.id.input))
                .perform(clearText(), typeText("tw"), clearText())

        t.assertValueCount(2)

        s.unsubscribe()

        onView(withId(android.R.id.input))
                .perform(clearText(), typeText("four"), clearText())

        t.assertValueCount(2)
    }

}