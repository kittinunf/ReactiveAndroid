package com.github.kittinunf.reactiveandroid.reactive.view

import android.support.test.InstrumentationRegistry
import android.support.test.annotation.UiThreadTest
import android.support.test.rule.ActivityTestRule
import android.support.test.runner.AndroidJUnit4
import android.view.View
import com.github.kittinunf.reactiveandroid.view.ViewTestActivity
import io.reactivex.subjects.BehaviorSubject
import org.hamcrest.CoreMatchers.equalTo
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class ViewTest {
    @Rule
    @JvmField
    val activityRule = ActivityTestRule(ViewTestActivity::class.java)

    val instrumentation = InstrumentationRegistry.getInstrumentation()

    lateinit var view: View

    @Before
    fun before() {
        view = activityRule.activity.child
    }

    @Test
    @UiThreadTest
    fun activated() {
        var activated = false

        val activatedSubject = BehaviorSubject.create<Boolean>()
        activatedSubject.onNext(activated)
        activatedSubject.subscribe(view.rx.activated)

        assertThat(view.isActivated, equalTo(activated))

        activated = true
        activatedSubject.onNext(activated)
        assertThat(view.isActivated, equalTo(activated))
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