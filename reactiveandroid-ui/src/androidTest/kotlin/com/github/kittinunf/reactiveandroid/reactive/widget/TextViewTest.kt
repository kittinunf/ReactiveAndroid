package com.github.kittinunf.reactiveandroid.reactive.widget

import android.content.res.ColorStateList
import android.graphics.Color
import android.graphics.Typeface
import android.support.test.InstrumentationRegistry
import android.support.test.annotation.UiThreadTest
import android.support.test.rule.ActivityTestRule
import android.support.test.runner.AndroidJUnit4
import android.view.inputmethod.EditorInfo
import com.github.kittinunf.reactiveandroid.widget.TextViewTestActivity
import io.reactivex.Single
import org.hamcrest.CoreMatchers.equalTo
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class TextViewTest {

    @Rule
    @JvmField
    val activityRule = ActivityTestRule(TextViewTestActivity::class.java)

    val instrumentation = InstrumentationRegistry.getInstrumentation()

    @Test
    @UiThreadTest
    fun afterTextChanged() {
        val view = activityRule.activity.textView
        val test = view.rx.afterTextChanged().test()
        val expectedText1 = "test"
        val expectedText2 = "testx"

        view.text = expectedText1
        view.text = expectedText2

        test.assertValueCount(2)
        test.assertNoErrors()

        assertThat(test.values()[0].s.toString(), equalTo(expectedText1))
        assertThat(test.values()[1].s.toString(), equalTo(expectedText2))
    }

    @Test
    @UiThreadTest
    fun beforeTextChanged() {
        val view = activityRule.activity.textView
        val test = view.rx.beforeTextChanged().test()

        val inputText1 = "h"
        val inputText2 = "he"
        val inputText3 = "he1"

        view.text = inputText1
        view.text = inputText2
        view.text = inputText3

        test.assertValueCount(3)
        test.assertNoErrors()

        assertThat(test.values()[0].s.toString(), equalTo(""))
        assertThat(test.values()[0].start, equalTo(0))
        assertThat(test.values()[0].count, equalTo(0))
        assertThat(test.values()[0].after, equalTo(1))

        assertThat(test.values()[1].s.toString(), equalTo(inputText1))
        assertThat(test.values()[1].start, equalTo(0))
        assertThat(test.values()[1].count, equalTo(1))
        assertThat(test.values()[1].after, equalTo(2))

        assertThat(test.values()[2].s.toString(), equalTo(inputText2))
        assertThat(test.values()[2].start, equalTo(0))
        assertThat(test.values()[2].count, equalTo(2))
        assertThat(test.values()[2].after, equalTo(3))
    }

    @Test
    @UiThreadTest
    fun textChanged() {
        val view = activityRule.activity.textView
        val test = view.rx.textChanged().test()

        val inputText1 = "h"
        val inputText2 = "he"
        val inputText3 = "he1"

        view.text = inputText1
        view.text = inputText2
        view.text = inputText3

        test.assertValueCount(4)
        test.assertNoErrors()

        assertThat(test.values()[0].s.toString(), equalTo(""))
        assertThat(test.values()[0].start, equalTo(0))
        assertThat(test.values()[0].before, equalTo(0))
        assertThat(test.values()[0].count, equalTo(0))

        assertThat(test.values()[1].s.toString(), equalTo(inputText1))
        assertThat(test.values()[1].start, equalTo(0))
        assertThat(test.values()[1].before, equalTo(0))
        assertThat(test.values()[1].count, equalTo(1))

        assertThat(test.values()[2].s.toString(), equalTo(inputText2))
        assertThat(test.values()[2].start, equalTo(0))
        assertThat(test.values()[2].before, equalTo(1))
        assertThat(test.values()[2].count, equalTo(2))

        assertThat(test.values()[3].s.toString(), equalTo(inputText3))
        assertThat(test.values()[3].start, equalTo(0))
        assertThat(test.values()[3].before, equalTo(2))
        assertThat(test.values()[3].count, equalTo(3))
    }

    @Test
    @UiThreadTest
    fun editorAction() {
        val view = activityRule.activity.textView
        val test = view.rx.onEditorAction().test()

        view.text = "h"
        view.onEditorAction(EditorInfo.IME_ACTION_NEXT)

        view.text = "he"
        view.onEditorAction(EditorInfo.IME_ACTION_DONE)

        test.assertValueCount(2)
        test.assertNoErrors()
    }

    @Test
    @UiThreadTest
    fun text() {
        val view = activityRule.activity.textView
        val expectedText = "text"

        Single.just(expectedText).subscribe(view.rx.text)

        assertThat(view.text.toString(), equalTo(expectedText))
    }

    @Test
    @UiThreadTest
    fun error() {
        val view = activityRule.activity.textView
        val expectedText = "error"

        Single.just(expectedText).subscribe(view.rx.error)

        assertThat(view.error.toString(), equalTo(expectedText))
    }

    @Test
    @UiThreadTest
    fun hint() {
        val view = activityRule.activity.textView
        val expectedText = "hint"

        Single.just(expectedText).subscribe(view.rx.hint)

        assertThat(view.hint.toString(), equalTo(expectedText))
    }

    @Test
    @UiThreadTest
    fun textColor() {
        val view = activityRule.activity.textView
        val expectedColor = 20

        Single.just(expectedColor).subscribe(view.rx.textColor)

        assertThat(view.currentTextColor, equalTo(expectedColor))
    }

    @Test
    @UiThreadTest
    fun textColors() {
        val view = activityRule.activity.textView

        val states = arrayOf(intArrayOf(android.R.attr.state_activated), intArrayOf(-android.R.attr.state_activated))
        val colors = intArrayOf(Color.parseColor("#FFFF00"), Color.BLACK)
        val expectedColors = ColorStateList(states, colors)

        Single.just(expectedColors).subscribe(view.rx.textColors)

        assertThat(view.textColors, equalTo(expectedColors))
    }

    @Test
    @UiThreadTest
    fun typeFace() {
        val view = activityRule.activity.textView
        val expectedTypeface = Typeface.DEFAULT

        Single.just(expectedTypeface).subscribe(view.rx.typeface)

        assertThat(view.typeface, equalTo(expectedTypeface))
    }

}
