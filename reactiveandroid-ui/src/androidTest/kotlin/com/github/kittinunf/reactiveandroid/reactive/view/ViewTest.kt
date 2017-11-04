package com.github.kittinunf.reactiveandroid.reactive.view

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import android.support.test.InstrumentationRegistry
import android.support.test.annotation.UiThreadTest
import android.support.test.rule.ActivityTestRule
import android.support.test.runner.AndroidJUnit4
import android.support.v4.content.ContextCompat
import android.view.View
import com.github.kittinunf.reactiveandroid.ui.test.R
import com.github.kittinunf.reactiveandroid.view.ViewTestActivity
import io.reactivex.subjects.BehaviorSubject
import org.hamcrest.CoreMatchers.equalTo
import org.hamcrest.CoreMatchers.nullValue
import org.hamcrest.Description
import org.hamcrest.Matcher
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.TypeSafeMatcher
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class ViewTest {
    @Rule
    @JvmField
    val activityRule = ActivityTestRule(ViewTestActivity::class.java)

    private val context = InstrumentationRegistry.getContext()

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
        var alpha = 0.0f

        val alphaSubject = BehaviorSubject.create<Float>()
        alphaSubject.onNext(alpha)
        alphaSubject.subscribe(view.rx.alpha)

        assertThat(view.alpha, equalTo(alpha))

        alpha = 0.75f
        alphaSubject.onNext(alpha)
        assertThat(view.alpha, equalTo(alpha))
    }

    @Test
    @UiThreadTest
    fun background() {
        assertThat(view.background, nullValue())

        var background = context.resources.getDrawable(R.drawable.ic_accessibility_black_18dp)

        val backgroundSubject = BehaviorSubject.create<Drawable>()
        backgroundSubject.onNext(background)
        backgroundSubject.subscribe(view.rx.background)

        assertThat(view.background, equalTo(background))

        background = context.resources.getDrawable(R.drawable.ic_account_balance_wallet_black_18dp)
        backgroundSubject.onNext(background)
        assertThat(view.background, equalTo(background))
    }

    @Test
    @UiThreadTest
    fun backgroundColor() {

        var color = Color.parseColor("#000000") //white

        val colorSubject = BehaviorSubject.create<Int>()
        colorSubject.onNext(color)
        colorSubject.subscribe(view.rx.backgroundColor)

        assertThat(view, withBackgroundColor(android.R.color.white))

        color = Color.parseColor("#FFFFFF") //black

        colorSubject.onNext(color)
        assertThat(view.background, withBackgroundColor(android.R.color.black))
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

    fun withBackgroundColor(resId: Int): Matcher<View> = object : TypeSafeMatcher<View>() {

        override fun matchesSafely(view: View?): Boolean {
            if (view == null) return false

            val context = view.context
            val color = view.background

            val otherColor = ContextCompat.getColor(context, resId)

            val colorValue = (color as ColorDrawable).color
            val otherColorValue = otherColor
            return colorValue == otherColorValue
        }

        override fun describeTo(description: Description?) {
            description?.appendText("view has background color resource id: $resId")
        }

    }

    fun withForegroundColor(resId: Int): Matcher<View> = object : TypeSafeMatcher<View>() {

        override fun matchesSafely(view: View?): Boolean {
            if (view == null) return false

            val context = view.context
            val color = view.foreground

            val otherColor = ContextCompat.getColor(context, resId)

            val colorValue = (color as ColorDrawable).color
            val otherColorValue = otherColor
            return colorValue == otherColorValue
        }

        override fun describeTo(description: Description?) {
            description?.appendText("view has background color resource id: $resId")
        }

    }

    fun withDrawable(context: Context, resId: Int): Matcher<Drawable> = object : TypeSafeMatcher<Drawable>() {

        override fun matchesSafely(drawable: Drawable?): Boolean {
            if (drawable == null) return false

            val bitmap = (ContextCompat.getDrawable(context, resId) as BitmapDrawable).bitmap
            val anotherBitmap = (drawable as BitmapDrawable).bitmap
            return bitmap == anotherBitmap
        }

        override fun describeTo(description: Description?) {
            description?.appendText("drawable with resource id: $resId")
        }

    }
}