package com.github.kittinunf.reactiveandroid.reactive.view

import android.support.test.InstrumentationRegistry
import android.support.test.annotation.UiThreadTest
import android.support.test.rule.ActivityTestRule
import android.support.test.runner.AndroidJUnit4
import com.github.kittinunf.reactiveandroid.view.ViewTestActivity
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class ViewTest {
    @Rule
    @JvmField
    val activityRule = ActivityTestRule(ViewTestActivity::class.java)

    val instrumentation = InstrumentationRegistry.getInstrumentation()

    @Test
    @UiThreadTest
    fun activated() {
    }

    @Test
    @UiThreadTest
    fun alpha() {
    }

    @Test
    @UiThreadTest
    fun background() {
    }

    @Test
    @UiThreadTest
    fun backgroundColor() {
    }

    @Test
    @UiThreadTest
    fun clickable() {
    }

    @Test
    @UiThreadTest
    fun enabled() {
    }

    @Test
    @UiThreadTest
    fun focused() {
    }

    @Test
    @UiThreadTest
    fun longClickable() {
    }

    @Test
    @UiThreadTest
    fun padding() {
    }

    @Test
    @UiThreadTest
    fun pressed() {
    }

    @Test
    @UiThreadTest
    fun selected() {
    }

    @Test
    @UiThreadTest
    fun visibility() {
    }

}