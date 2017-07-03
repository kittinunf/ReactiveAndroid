package com.github.kittinunf.reactiveandroid.view

import android.annotation.TargetApi
import android.os.Build
import android.support.test.InstrumentationRegistry
import android.support.test.annotation.UiThreadTest
import android.support.test.filters.SdkSuppress
import android.support.test.rule.UiThreadTestRule
import android.support.test.runner.AndroidJUnit4
import android.view.KeyEvent
import android.view.View
import android.widget.LinearLayout
import io.reactivex.observers.TestObserver
import org.hamcrest.CoreMatchers.equalTo
import org.junit.Assert.assertThat
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class ViewEventTest {

    @Rule
    @JvmField
    val uiThreadTestRule = UiThreadTestRule()

    private val context = InstrumentationRegistry.getContext()
    private val view = View(context)

    @Test
    @UiThreadTest
    fun testClick() {
        val t = TestObserver<View>()
        val subscription = view.rx_click().subscribeWith(t)

        t.assertNoErrors()
        t.assertNoValues()

        view.performClick()
        t.assertValueCount(1)

        view.performClick()
        t.assertValueCount(2)

        subscription.dispose()

        view.performClick()
        t.assertValueCount(2)
    }

    @Test
    @UiThreadTest
    fun testDrag() {
    }

    @Test
    @UiThreadTest
    fun testKey() {
        val t = TestObserver<Int>()
        val subscription = view.rx_key(true).map { it.keyCode }.subscribeWith(t)

        t.assertNoErrors()
        t.assertNoValues()

        view.dispatchKeyEvent(KeyEvent(KeyEvent.ACTION_DOWN, KeyEvent.KEYCODE_A))
        view.dispatchKeyEvent(KeyEvent(KeyEvent.ACTION_DOWN, KeyEvent.KEYCODE_S))
        view.dispatchKeyEvent(KeyEvent(KeyEvent.ACTION_DOWN, KeyEvent.KEYCODE_D))
        view.dispatchKeyEvent(KeyEvent(KeyEvent.ACTION_DOWN, KeyEvent.KEYCODE_F))

        t.assertValues(KeyEvent.KEYCODE_A, KeyEvent.KEYCODE_S, KeyEvent.KEYCODE_D, KeyEvent.KEYCODE_F)

        subscription.dispose()

        view.dispatchKeyEvent(KeyEvent(KeyEvent.ACTION_DOWN, KeyEvent.KEYCODE_Q))
        view.dispatchKeyEvent(KeyEvent(KeyEvent.ACTION_DOWN, KeyEvent.KEYCODE_W))

        t.assertValueCount(4)
    }

    @Test
    @UiThreadTest
    fun testHover() {
    }

    @Test
    @UiThreadTest
    fun testTouch() {
    }

    @Test
    @UiThreadTest
    fun testLongClick() {
        val t = TestObserver<View>()
        val subscription = view.rx_longClick(true).subscribeWith(t)

        t.assertNoErrors()
        t.assertNoValues()

        view.performLongClick()
        t.assertValueCount(1)

        view.performLongClick()
        t.assertValueCount(2)

        subscription.dispose()

        view.performClick()
        t.assertValueCount(2)
    }

    @Test
    @UiThreadTest
    fun testFocusChange() {
        val t = TestObserver<Boolean>()
        val subscription = view.rx_focusChange().map { it.hasFocus }.subscribeWith(t)

        LinearLayout(context).apply {
            isFocusable = true
            addView(view)
        }
        view.isFocusable = true

        t.assertNoErrors()
        t.assertNoValues()

        view.requestFocus()
        t.assertValueCount(1)

        view.clearFocus()
        t.assertValueCount(2)

        t.assertValues(true, false)
        subscription.dispose()

        view.requestFocus()
        t.assertValueCount(2)
    }

    @Test
    @UiThreadTest
    fun testLayoutChange() {
        val t = TestObserver<LayoutChangeListener>()
        val subscription = view.rx_layoutChange().subscribeWith(t)

        t.assertNoErrors()
        t.assertNoValues()

        view.layout(view.left + 1, view.top + 2, view.right + 3, view.bottom + 4)

        t.assertValueCount(1)
        val first = t.values().toList().first()

        assertThat(first.newRect.left, equalTo(view.left))
        assertThat(first.newRect.top, equalTo(view.top))
        assertThat(first.newRect.right, equalTo(view.right))
        assertThat(first.newRect.bottom, equalTo(view.bottom))

        subscription.dispose()
        view.layout(view.left + 1, view.top + 2, view.right + 3, view.bottom + 4)
        t.assertValueCount(1)
    }

    @TargetApi(Build.VERSION_CODES.M)
    @SdkSuppress(minSdkVersion = Build.VERSION_CODES.M)
    @Test
    @UiThreadTest
    fun testScrollChange() {
        val t = TestObserver<ScrollChangeListener>()
        val subscription = view.rx_scrollChange().subscribeWith(t)

        t.assertNoErrors()
        t.assertNoValues()

        view.scrollTo(10, 20)
        val first = t.values().first()

        assertThat(first.oldDirection.x, equalTo(0))
        assertThat(first.direction.x, equalTo(10))
        assertThat(first.oldDirection.y, equalTo(0))
        assertThat(first.direction.y, equalTo(20))

        view.scrollTo(200, 100)
        val second = t.values()[1]
        assertThat(second.oldDirection.x, equalTo(10))
        assertThat(second.direction.x, equalTo(200))
        assertThat(second.oldDirection.y, equalTo(20))
        assertThat(second.direction.y, equalTo(100))

        subscription.dispose()
        view.scrollTo(1000, 1000)
        t.assertValueCount(2)
    }

    @Test
    @UiThreadTest
    fun testCreateContextMenu() {
    }

}
