package com.github.kittinunf.reactiveandroid.view

import android.support.test.InstrumentationRegistry
import android.support.test.annotation.UiThreadTest
import android.support.test.rule.UiThreadTestRule
import android.support.test.runner.AndroidJUnit4
import android.support.v4.content.ContextCompat
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.github.kittinunf.reactiveandroid.reactive.bindTo
import com.github.kittinunf.reactiveandroid.reactive.view.TestMenuItem
import com.github.kittinunf.reactiveandroid.scheduler.AndroidThreadScheduler
import com.github.kittinunf.reactiveandroid.ui.test.R
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers
import org.hamcrest.CoreMatchers.equalTo
import org.hamcrest.CoreMatchers.nullValue
import org.junit.Assert.assertThat
import org.junit.BeforeClass
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class MenuItemPropertyTest {

    @Rule
    @JvmField
    val uiThreadTestRule = UiThreadTestRule()

    private val context = InstrumentationRegistry.getContext()

    private val menuItem = TestMenuItem(context)

    companion object {
        @BeforeClass
        @JvmStatic
        fun setUp() {
            AndroidThreadScheduler.main = Schedulers.trampoline()
        }
    }

    @Test
    @UiThreadTest
    fun testActionView() {
        val actionView = menuItem.rx_actionView
        assertThat(menuItem.actionView, nullValue())

        val view = ImageView(context) as View
        Observable.just(view).bindTo(actionView)

        assertThat(menuItem.actionView, equalTo(view))

        val anotherView = TextView(context) as View
        actionView.bindTo(Observable.just(anotherView))

        assertThat(menuItem.actionView, equalTo(anotherView))
    }

    @Test
    @UiThreadTest
    fun testIcon() {
        val icon = menuItem.rx_icon
        assertThat(menuItem.icon, nullValue())

        val drawable = ContextCompat.getDrawable(context, R.drawable.ic_accessibility_black_18dp)
        Observable.just(drawable).bindTo(icon)
        assertThat(menuItem.icon, equalTo(drawable))

        val anotherDrawable = ContextCompat.getDrawable(context, R.drawable.ic_account_balance_wallet_black_18dp)
        icon.bindTo(Observable.just(anotherDrawable))
        assertThat(menuItem.icon, equalTo(anotherDrawable))
    }

    @Test
    @UiThreadTest
    fun testCheckable() {
        val checkable = menuItem.rx_checkable
        assertThat(menuItem.isCheckable, equalTo(false))

        Observable.just(true).bindTo(checkable)
        assertThat(menuItem.isCheckable, equalTo(true))

        checkable.bindTo(Observable.just(false))
        assertThat(menuItem.isCheckable, equalTo(false))
    }

    @Test
    @UiThreadTest
    fun testChecked() {
        val checked = menuItem.rx_checked
        assertThat(menuItem.isChecked, equalTo(false))

        Observable.just(true).bindTo(checked)
        assertThat(menuItem.isChecked, equalTo(true))

        checked.bindTo(Observable.just(false))
        assertThat(menuItem.isChecked, equalTo(false))
    }

    @Test
    @UiThreadTest
    fun testEnabled() {
        val enabled = menuItem.rx_enabled
        assertThat(menuItem.isEnabled, equalTo(false))

        Observable.just(true).bindTo(enabled)
        assertThat(menuItem.isEnabled, equalTo(true))

        enabled.bindTo(Observable.just(false))
        assertThat(menuItem.isEnabled, equalTo(false))
    }

    @Test
    @UiThreadTest
    fun testVisible() {
        val enabled = menuItem.rx_visible

        Observable.just(true).bindTo(enabled)
        assertThat(menuItem.isVisible, equalTo(true))

        enabled.bindTo(Observable.just(false))
        assertThat(menuItem.isVisible, equalTo(false))
    }

    @Test
    @UiThreadTest
    fun testTitle() {
        val title = menuItem.rx_title

        Observable.just("hello").bindTo(title)
        assertThat(menuItem.title, equalTo("hello" as CharSequence))

        title.bindTo(Observable.just("world" as CharSequence))
        assertThat(menuItem.title, equalTo("world" as CharSequence))
    }

}
