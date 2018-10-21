package com.github.kittinunf.reactiveandroid.reactive.view

import android.graphics.drawable.Drawable
import android.support.test.InstrumentationRegistry
import android.support.test.annotation.UiThreadTest
import android.support.test.runner.AndroidJUnit4
import android.support.v4.content.ContextCompat
import android.view.MenuItem
import android.view.View
import android.widget.TextView
import com.github.kittinunf.reactiveandroid.ui.test.R
import io.reactivex.subjects.BehaviorSubject
import org.awaitility.Awaitility.await
import org.hamcrest.CoreMatchers.equalTo
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class MenuItemTest {

    val context = InstrumentationRegistry.getContext()

    lateinit var view: MenuItem

    @Before
    fun before() {
        view = TestMenuItem(context)
    }

    @Test
    @UiThreadTest
    fun actionView() {
        val subject = BehaviorSubject.create<View>()
        subject.subscribe(view.rx.actionView)

        var fakeView = View(context)
        subject.onNext(fakeView)
        await().untilAsserted { assertThat(view.actionView, equalTo(fakeView)) }

        fakeView = TextView(context)
        subject.onNext(fakeView)
        await().untilAsserted { assertThat(view.actionView, equalTo(fakeView)) }
    }

    @Test
    @UiThreadTest
    fun actionViewResourceId() {
    }

    @Test
    @UiThreadTest
    fun icon() {
        val subject = BehaviorSubject.create<Drawable>()
        subject.subscribe(view.rx.icon)

        var drawable = ContextCompat.getDrawable(context, R.drawable.ic_accessibility_black_18dp)
        subject.onNext(drawable)
        await().untilAsserted { assertThat(view.icon, equalTo(drawable)) }

        drawable = ContextCompat.getDrawable(context, R.drawable.ic_account_balance_wallet_black_18dp)
        subject.onNext(drawable)
        await().untilAsserted { assertThat(view.icon, equalTo(drawable)) }
    }

    @Test
    @UiThreadTest
    fun iconResourceId() {
        val subject = BehaviorSubject.create<Int>()
        subject.subscribe(view.rx.iconResourceId)

        var resId = R.drawable.ic_accessibility_black_18dp
        subject.onNext(resId)
        await().untilAsserted { assertThat(view.icon, withDrawable(context, resId)) }

        resId = R.drawable.ic_account_balance_wallet_black_18dp
        subject.onNext(resId)
        await().untilAsserted { assertThat(view.icon, withDrawable(context, resId)) }
    }

    @Test
    @UiThreadTest
    fun enabled() {
        val subject = BehaviorSubject.create<Boolean>()
        subject.subscribe(view.rx.enabled)

        var enabled = false
        subject.onNext(enabled)
        await().untilAsserted { assertThat(view.isEnabled, equalTo(enabled)) }

        enabled = true
        subject.onNext(enabled)
        await().untilAsserted { assertThat(view.isEnabled, equalTo(enabled)) }
    }

    @Test
    @UiThreadTest
    fun visible() {
        val subject = BehaviorSubject.create<Boolean>()
        subject.subscribe(view.rx.visible)

        var visible = false
        subject.onNext(visible)
        await().untilAsserted { assertThat(view.isVisible, equalTo(visible)) }

        visible = true
        subject.onNext(visible)
        await().untilAsserted { assertThat(view.isVisible, equalTo(visible)) }
    }

    @Test
    @UiThreadTest
    fun title() {
        val subject = BehaviorSubject.create<CharSequence>()
        subject.subscribe(view.rx.title)

        var title = "abc" as CharSequence
        subject.onNext(title)
        await().untilAsserted { assertThat(view.title, equalTo(title)) }

        title = "def"
        subject.onNext(title)
        await().untilAsserted { assertThat(view.title, equalTo(title)) }
    }
}