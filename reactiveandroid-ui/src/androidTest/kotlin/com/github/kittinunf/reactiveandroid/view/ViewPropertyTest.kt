package com.github.kittinunf.reactiveandroid.view

import android.annotation.TargetApi
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import android.os.Build
import android.support.test.InstrumentationRegistry
import android.support.test.annotation.UiThreadTest
import android.support.test.filters.SdkSuppress
import android.support.test.rule.UiThreadTestRule
import android.support.test.runner.AndroidJUnit4
import android.support.v4.content.ContextCompat
import android.view.View
import android.widget.LinearLayout
import com.github.kittinunf.reactiveandroid.reactive.bindTo
import com.github.kittinunf.reactiveandroid.scheduler.AndroidThreadScheduler
import com.github.kittinunf.reactiveandroid.ui.test.R
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers
import org.awaitility.Awaitility.await
import org.hamcrest.CoreMatchers.equalTo
import org.hamcrest.CoreMatchers.nullValue
import org.hamcrest.Description
import org.hamcrest.Matcher
import org.hamcrest.TypeSafeMatcher
import org.junit.Assert.assertThat
import org.junit.BeforeClass
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class ViewPropertyTest {

    @Rule
    @JvmField
    val uiThreadTestRule = UiThreadTestRule()

    private val context = InstrumentationRegistry.getContext()
    private val instrument = InstrumentationRegistry.getInstrumentation()
    private val view = View(context)

    companion object {
        @BeforeClass
        @JvmStatic
        fun setUp() {
            AndroidThreadScheduler.main = Schedulers.trampoline()
        }
    }

    @Test
    @UiThreadTest
    fun testActivate() {
        val activated = view.rx_activated
        assertThat(view.isActivated, equalTo(false))

        val o = Observable.just(true)
        activated.bindTo(o)

        assertThat(view.isActivated, equalTo(true))

        Observable.just(false).bindTo(activated)
        assertThat(view.isActivated, equalTo(false))
    }

    @Test
    @UiThreadTest
    fun testAlpha() {
        val alpha = view.rx_alpha
        view.alpha = 0.0f
        assertThat(view.alpha, equalTo(0.0f))

        val o = Observable.range(0, 10).map { ((it + 1) / 10).toFloat() }
        alpha.bindTo(o)

        await().until({ view.alpha }, equalTo(1.0f))
    }

    @Test
    @UiThreadTest
    fun testBackground() {
        val background = view.rx_background
        assertThat(view.background, nullValue())

        val backgroundDrawable = context.resources.getDrawable(R.drawable.ic_accessibility_black_18dp)
        Observable.just(backgroundDrawable).bindTo(background)
        assertThat(view, withBackground(R.drawable.ic_accessibility_black_18dp))

        val anotherBackgroundDrawable = context.resources.getDrawable(R.drawable.ic_account_balance_wallet_black_18dp)
        background.bindTo(Observable.just(anotherBackgroundDrawable))
        assertThat(view, withBackground(R.drawable.ic_account_balance_wallet_black_18dp))
    }

    @Test
    @UiThreadTest
    fun testBackgroundColor() {
        view.setBackgroundColor(Color.WHITE)
        assertThat(view, withBackgroundColor(android.R.color.white))

        val backgroundColor = view.rx_backgroundColor

        Observable.just(Color.BLACK).bindTo(backgroundColor)
        assertThat(view, withBackgroundColor(android.R.color.black))

        backgroundColor.bindTo(Observable.just(Color.TRANSPARENT))
        assertThat(view, withBackgroundColor(android.R.color.transparent))
    }

    @Test
    @UiThreadTest
    fun testClickable() {
        assertThat(view.isClickable, equalTo(false))

        val clickable = view.rx_clickable

        Observable.just(true).bindTo(clickable)
        assertThat(view.isClickable, equalTo(true))

        clickable.bindTo(Observable.just(false))
        assertThat(view.isClickable, equalTo(false))
    }

    @Test
    @UiThreadTest
    fun testEnabled() {
        assertThat(view.isEnabled, equalTo(true))

        val enabled = view.rx_enabled

        Observable.just(false).bindTo(enabled)
        assertThat(view.isEnabled, equalTo(false))

        enabled.bindTo(Observable.just(true))
        assertThat(view.isEnabled, equalTo(true))
    }

    @Test
    @UiThreadTest
    fun testFocused() {
        assertThat(view.isFocused, equalTo(false))

        LinearLayout(context).apply {
            isFocusable = true
            addView(view)
        }
        view.isFocusable = true

        val focused = view.rx_focused

        Observable.just(true).bindTo(focused)
        assertThat(view.isFocused, equalTo(true))

        focused.bindTo(Observable.just(false))
        assertThat(view.isFocused, equalTo(false))
    }

    @TargetApi(Build.VERSION_CODES.M)
    @SdkSuppress(minSdkVersion = Build.VERSION_CODES.M)
    @Test
    @UiThreadTest
    fun testForeground() {
        assertThat(view.foreground, nullValue())

        val foreground = view.rx_foreground

        val colorDrawable = ColorDrawable(Color.BLACK)
        Observable.just(colorDrawable).bindTo(foreground)
        assertThat(view, withForegroundColor(android.R.color.black))

        val anotherColorDrawable = ColorDrawable(Color.WHITE)

        val anotherDrawable = Observable.just(anotherColorDrawable) as Observable<Drawable>
        foreground.bindTo(anotherDrawable)
        assertThat(view, withForegroundColor(android.R.color.white))
    }

    @Test
    @UiThreadTest
    fun testLongClickable() {
        assertThat(view.isLongClickable, equalTo(false))

        val longClickable = view.rx_longClickable

        Observable.just(true).bindTo(longClickable)
        assertThat(view.isLongClickable, equalTo(true))

        longClickable.bindTo(Observable.just(false))
        assertThat(view.isLongClickable, equalTo(false))
    }

    @Test
    @UiThreadTest
    fun testPadding() {
        val padding = view.rx_padding

        Observable.just(Padding(1, 2, 3, 4)).bindTo(padding)
        assertThat(view.paddingStart, equalTo(1))
        assertThat(view.paddingTop, equalTo(2))
        assertThat(view.paddingEnd, equalTo(3))
        assertThat(view.paddingBottom, equalTo(4))

        padding.bindTo(Observable.just(Padding(40, 30, 20, 10)))
        assertThat(view.paddingStart, equalTo(40))
        assertThat(view.paddingTop, equalTo(30))
        assertThat(view.paddingEnd, equalTo(20))
        assertThat(view.paddingBottom, equalTo(10))
    }

    @Test
    @UiThreadTest
    fun testLongPressed() {
        assertThat(view.isPressed, equalTo(false))

        val pressed = view.rx_pressed

        Observable.just(true).bindTo(pressed)
        assertThat(view.isPressed, equalTo(true))

        pressed.bindTo(Observable.just(false))
        assertThat(view.isPressed, equalTo(false))
    }

    @Test
    @UiThreadTest
    fun testSelected() {
        assertThat(view.isSelected, equalTo(false))

        val selected = view.rx_selected

        Observable.just(true).bindTo(selected)
        assertThat(view.isSelected, equalTo(true))

        selected.bindTo(Observable.just(false))
        assertThat(view.isSelected, equalTo(false))
    }

    @Test
    @UiThreadTest
    fun testVisibility() {
        assertThat(view.visibility, equalTo(View.VISIBLE))

        val visible = view.rx_visibility

        Observable.just(View.GONE).bindTo(visible)
        assertThat(view.visibility, equalTo(View.GONE))

        visible.bindTo(Observable.just(View.INVISIBLE))
        assertThat(view.visibility, equalTo(View.INVISIBLE))

        Observable.just(View.VISIBLE).bindTo(visible)
        assertThat(view.visibility, equalTo(View.VISIBLE))
    }

}

//helpers
fun withBackground(resId: Int): Matcher<View> = object : TypeSafeMatcher<View>() {

    override fun matchesSafely(view: View?): Boolean {
        if (view == null) return false

        val context = view.context
        val background = view.background

        val otherDrawable = ContextCompat.getDrawable(context, resId) ?: return false

        val bitmap = (background as BitmapDrawable).bitmap
        val otherBitmap = (otherDrawable as BitmapDrawable).bitmap
        return bitmap.sameAs(otherBitmap)
    }

    override fun describeTo(description: Description?) {
        description?.appendText("view has background resource id: $resId")
    }

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



