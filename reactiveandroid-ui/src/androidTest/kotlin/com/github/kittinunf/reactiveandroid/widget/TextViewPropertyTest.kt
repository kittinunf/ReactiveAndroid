package com.github.kittinunf.reactiveandroid.widget

import android.graphics.Color
import android.graphics.Typeface
import android.support.test.InstrumentationRegistry
import android.support.test.annotation.UiThreadTest
import android.support.test.rule.ActivityTestRule
import android.support.test.runner.AndroidJUnit4
import com.github.kittinunf.reactiveandroid.reactive.bindTo
import com.github.kittinunf.reactiveandroid.scheduler.AndroidThreadScheduler
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers
import org.hamcrest.CoreMatchers.equalTo
import org.hamcrest.MatcherAssert.assertThat
import org.junit.BeforeClass
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class TextViewPropertyTest {

    @Rule
    @JvmField
    val activityRule = ActivityTestRule(TextViewTestActivity::class.java)

    val instrumentation = InstrumentationRegistry.getInstrumentation()

    private val context = InstrumentationRegistry.getContext()

    companion object {
        @BeforeClass
        @JvmStatic
        fun setUp() {
            AndroidThreadScheduler.main = Schedulers.trampoline()
        }
    }

    @Test
    @UiThreadTest
    fun testError() {
        val view = activityRule.activity.textView
        val error = view.rx_error

        Observable.just("error_text").bindTo(error)
        assertThat(view.error.toString(), equalTo("error_text"))

        error.bindTo(Observable.just("another_error_text") as Observable<CharSequence>)
        assertThat(view.error.toString(), equalTo("another_error_text"))
    }

    @Test
    @UiThreadTest
    fun testHint() {
        val view = activityRule.activity.textView
        val hint = view.rx_hint

        Observable.just("hint_text").bindTo(hint)
        assertThat(view.hint.toString(), equalTo("hint_text"))

        hint.bindTo(Observable.just("another_hint_text") as Observable<CharSequence>)
        assertThat(view.hint.toString(), equalTo("another_hint_text"))
    }

    @Test
    @UiThreadTest
    fun testTextColor() {
        val view = activityRule.activity.textView
        val textColor = view.rx_textColor

        Observable.just(Color.RED).bindTo(textColor)
        assertThat(view.currentTextColor, equalTo(Color.RED))

        textColor.bindTo(Observable.just(Color.GREEN))
        assertThat(view.currentTextColor, equalTo(Color.GREEN))
    }

    @Test
    @UiThreadTest
    fun testTypeface() {
        val view = activityRule.activity.textView
        val typeface = view.rx_typeface

        val sameTypeFace: Typeface? = null

        Observable.just(Typeface.create(sameTypeFace, Typeface.BOLD)).bindTo(typeface)
        assertThat(view.typeface.isBold, equalTo(true))

        typeface.bindTo(Observable.just(Typeface.create(sameTypeFace, Typeface.ITALIC)))
        assertThat(view.typeface.isItalic, equalTo(true))
    }

}
