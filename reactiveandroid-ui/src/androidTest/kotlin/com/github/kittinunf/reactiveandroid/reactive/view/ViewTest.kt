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
import android.view.KeyEvent
import android.view.View
import com.github.kittinunf.reactiveandroid.ui.test.R
import com.github.kittinunf.reactiveandroid.view.Padding
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

    val context = InstrumentationRegistry.getContext()

    val instrumentation = InstrumentationRegistry.getInstrumentation()

    lateinit var view: View

    @Before
    fun before() {
        view = activityRule.activity.child
    }

    @Test
    @UiThreadTest
    fun click() {
        val test = view.rx.click().test()

        view.performClick()
        view.performClick()
        view.performClick()

        test.assertValueCount(3)
        test.assertNoErrors()

        test.dispose()

        view.performClick()
        view.performClick()
        test.assertValueCount(3)
    }

    @Test
    @UiThreadTest
    fun drag() {
    }

    @Test
    @UiThreadTest
    fun key() {
        val test = view.rx.key().map { it.keyCode }.test()

        view.dispatchKeyEvent(KeyEvent(KeyEvent.ACTION_DOWN, KeyEvent.KEYCODE_A))
        view.dispatchKeyEvent(KeyEvent(KeyEvent.ACTION_DOWN, KeyEvent.KEYCODE_S))
        view.dispatchKeyEvent(KeyEvent(KeyEvent.ACTION_DOWN, KeyEvent.KEYCODE_D))
        view.dispatchKeyEvent(KeyEvent(KeyEvent.ACTION_DOWN, KeyEvent.KEYCODE_F))

        test.assertValues(KeyEvent.KEYCODE_A, KeyEvent.KEYCODE_S, KeyEvent.KEYCODE_D, KeyEvent.KEYCODE_F)

        test.dispose()

        view.dispatchKeyEvent(KeyEvent(KeyEvent.ACTION_DOWN, KeyEvent.KEYCODE_Q))
        view.dispatchKeyEvent(KeyEvent(KeyEvent.ACTION_DOWN, KeyEvent.KEYCODE_W))

        test.assertValueCount(4)
    }

    @Test
    @UiThreadTest
    fun hover() {
    }

    @Test
    @UiThreadTest
    fun touch() {
    }

    @Test
    @UiThreadTest
    fun longClick() {
        val test = view.rx.longClick().test()

        view.performLongClick()
        view.performLongClick()
        view.performLongClick()

        test.assertValueCount(3)
        test.assertNoErrors()

        test.dispose()

        view.performLongClick()
        view.performLongClick()
        test.assertValueCount(3)
    }

    @Test
    @UiThreadTest
    fun focusChange() {
        view.isFocusable = true

        val test = view.rx.focusChange().map { it.hasFocus }.test()

        view.requestFocus()
        test.assertValueCount(1)

        view.clearFocus()
        test.assertValueCount(2)

        test.assertValues(true, false)
        test.dispose()

        view.requestFocus()
        view.clearFocus()
        test.assertValueCount(2)
    }

    @Test
    @UiThreadTest
    fun activated() {
        var activated = false

        val subject = BehaviorSubject.create<Boolean>()
        subject.onNext(activated)
        subject.subscribe(view.rx.activated)

        assertThat(view.isActivated, equalTo(activated))

        activated = true
        subject.onNext(activated)
        assertThat(view.isActivated, equalTo(activated))
    }

    @Test
    @UiThreadTest
    fun alpha() {
        var alpha = 0.0f

        val subject = BehaviorSubject.create<Float>()
        subject.onNext(alpha)
        subject.subscribe(view.rx.alpha)

        assertThat(view.alpha, equalTo(alpha))

        alpha = 0.75f
        subject.onNext(alpha)
        assertThat(view.alpha, equalTo(alpha))
    }

    @Test
    @UiThreadTest
    fun background() {

        assertThat(view.background, nullValue())

        var background = context.resources.getDrawable(R.drawable.ic_accessibility_black_18dp)

        val subject = BehaviorSubject.create<Drawable>()
        subject.onNext(background)
        subject.subscribe(view.rx.background)

        assertThat(view.background, equalTo(background))

        background = context.resources.getDrawable(R.drawable.ic_account_balance_wallet_black_18dp)
        subject.onNext(background)
        assertThat(view.background, equalTo(background))
    }

    @Test
    @UiThreadTest
    fun backgroundColor() {

        var color = Color.parseColor("#FFFFFF") //white

        val subject = BehaviorSubject.create<Int>()
        subject.onNext(color)
        subject.subscribe(view.rx.backgroundColor)

        assertThat(view, withBackgroundColor(android.R.color.white))

        color = Color.parseColor("#000000") //black

        subject.onNext(color)
        assertThat(view, withBackgroundColor(android.R.color.black))
    }

    @Test
    @UiThreadTest
    fun clickable() {
        var clickable = false

        val subject = BehaviorSubject.create<Boolean>()
        subject.onNext(clickable)
        subject.subscribe(view.rx.clickable)

        assertThat(view.isClickable, equalTo(clickable))

        clickable = true
        subject.onNext(clickable)
        assertThat(view.isClickable, equalTo(clickable))
    }

    @Test
    @UiThreadTest
    fun enabled() {
        var enabled = false

        val subject = BehaviorSubject.create<Boolean>()
        subject.onNext(enabled)
        subject.subscribe(view.rx.enabled)

        assertThat(view.isEnabled, equalTo(enabled))

        enabled = true
        subject.onNext(enabled)
        assertThat(view.isEnabled, equalTo(enabled))
    }

    @Test
    @UiThreadTest
    fun focused() {
        view.isFocusable = true

        var focused = false

        val subject = BehaviorSubject.create<Boolean>()
        subject.onNext(focused)
        subject.subscribe(view.rx.focused)

        assertThat(view.isFocused, equalTo(focused))

        focused = true
        subject.onNext(focused)
        assertThat(view.isFocused, equalTo(focused))
    }

    @Test
    @UiThreadTest
    fun longClickable() {
        var longClickable = false

        val subject = BehaviorSubject.create<Boolean>()
        subject.onNext(longClickable)
        subject.subscribe(view.rx.longClickable)

        assertThat(view.isLongClickable, equalTo(longClickable))

        longClickable = true
        subject.onNext(longClickable)
        assertThat(view.isLongClickable, equalTo(longClickable))
    }

    @Test
    @UiThreadTest
    fun padding() {
        var padding = Padding(10, 20, 30, 40)

        val subject = BehaviorSubject.create<Padding>()
        subject.onNext(padding)
        subject.subscribe(view.rx.padding)

        assertThat(view.paddingStart, equalTo(10))
        assertThat(view.paddingTop, equalTo(20))
        assertThat(view.paddingEnd, equalTo(30))
        assertThat(view.paddingBottom, equalTo(40))
    }

    @Test
    @UiThreadTest
    fun pressed() {
        var pressed = false

        val subject = BehaviorSubject.create<Boolean>()
        subject.onNext(pressed)
        subject.subscribe(view.rx.pressed)

        assertThat(view.isPressed, equalTo(pressed))

        pressed = true
        subject.onNext(pressed)
        assertThat(view.isPressed, equalTo(pressed))
    }

    @Test
    @UiThreadTest
    fun selected() {
        var selected = false

        val subject = BehaviorSubject.create<Boolean>()
        subject.onNext(selected)
        subject.subscribe(view.rx.selected)

        assertThat(view.isSelected, equalTo(selected))

        selected = true
        subject.onNext(selected)
        assertThat(view.isSelected, equalTo(selected))
    }

    @Test
    @UiThreadTest
    fun visibility() {
        var visibility = View.GONE

        val subject = BehaviorSubject.create<Int>()
        subject.onNext(visibility)
        subject.subscribe(view.rx.visibility)

        assertThat(view.visibility, equalTo(View.GONE))

        visibility = View.VISIBLE
        subject.onNext(visibility)
        assertThat(view.visibility, equalTo(View.VISIBLE))
    }

    @Test
    @UiThreadTest
    fun visible() {
        var visible = false

        val subject = BehaviorSubject.create<Boolean>()
        subject.onNext(visible)
        subject.subscribe(view.rx.visible)

        assertThat(view.visibility, equalTo(View.GONE))

        visible = true
        subject.onNext(visible)
        assertThat(view.visibility, equalTo(View.VISIBLE))
    }

    fun withBackgroundColor(resId: Int): Matcher<View> = object : TypeSafeMatcher<View>() {

        override fun matchesSafely(view: View?): Boolean {
            if (view == null) return false

            val context = view.context
            val color = view.background

            val otherColor = ContextCompat.getColor(context, resId)

            val colorValue = (color as ColorDrawable).color
            return colorValue == otherColor
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
            return colorValue == otherColor
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