package com.github.kittinunf.reactiveandroid.reactive.widget

import android.support.test.InstrumentationRegistry
import android.support.test.rule.ActivityTestRule
import android.support.test.runner.AndroidJUnit4
import android.support.v7.widget.RecyclerView
import com.github.kittinunf.reactiveandroid.reactive.activity.RecyclerViewTestActivity
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class RecyclerViewTest {

    @Rule
    @JvmField
    val activityRule = ActivityTestRule(RecyclerViewTestActivity::class.java)

    val instrumentation = InstrumentationRegistry.getInstrumentation()

    lateinit var view: RecyclerView

    @Before
    fun before() {
        view = activityRule.activity.recyclerView
    }

    @Test
    fun scrollStateChanged() {

    }

    @Test
    fun scrolled() {

    }
}