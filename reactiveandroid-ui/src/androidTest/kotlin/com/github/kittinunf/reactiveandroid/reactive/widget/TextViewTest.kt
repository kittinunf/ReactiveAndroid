package com.github.kittinunf.reactiveandroid.reactive.widget

import android.support.test.InstrumentationRegistry
import android.support.test.annotation.UiThreadTest
import android.support.test.rule.ActivityTestRule
import android.support.test.runner.AndroidJUnit4
import com.github.kittinunf.reactiveandroid.widget.TextViewTestActivity
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
    }

    @Test
    @UiThreadTest
    fun beforeTextChanged() {
        val view = activityRule.activity.textView
    }

    @Test
    @UiThreadTest
    fun onTextChanged() {
        val view = activityRule.activity.textView
    }

    @Test
    @UiThreadTest
    fun hint() {

    }

    @Test
    @UiThreadTest
    fun textColor() {

    }

    @Test
    @UiThreadTest
    fun typeFace() {

    }
}
