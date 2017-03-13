package com.github.kittinunf.reactiveandroid.widget

import android.annotation.TargetApi
import android.graphics.PorterDuff
import android.os.Build
import android.support.test.InstrumentationRegistry
import android.support.test.annotation.UiThreadTest
import android.support.test.filters.SdkSuppress
import android.support.test.rule.UiThreadTestRule
import android.support.test.runner.AndroidJUnit4
import android.support.v4.content.ContextCompat
import android.widget.CheckedTextView
import com.github.kittinunf.reactiveandroid.rx.bindTo
import com.github.kittinunf.reactiveandroid.scheduler.AndroidThreadScheduler
import com.github.kittinunf.reactiveandroid.ui.test.R
import com.github.kittinunf.reactiveandroid.view.withDrawable
import org.hamcrest.CoreMatchers.equalTo
import org.hamcrest.MatcherAssert.assertThat
import org.junit.BeforeClass
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import rx.Observable
import rx.schedulers.Schedulers

@RunWith(AndroidJUnit4::class)
class CheckedTextViewPropertyTest {

    @Rule
    @JvmField
    val uiThreadTestRule = UiThreadTestRule()

    private val context = InstrumentationRegistry.getContext()
    private val instrument = InstrumentationRegistry.getInstrumentation()
    private val view = CheckedTextView(context)

    companion object {
        @BeforeClass
        @JvmStatic
        fun setUp() {
            AndroidThreadScheduler.main = Schedulers.immediate()
        }
    }

    @Test
    @UiThreadTest
    fun testChecked() {
        val checked = view.rx_checked

        Observable.just(true).bindTo(checked)
        assertThat(view.isChecked, equalTo(true))

        checked.bindTo(Observable.just(false))
        assertThat(view.isChecked, equalTo(false))
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @SdkSuppress(minSdkVersion = Build.VERSION_CODES.LOLLIPOP)
    @Test
    @UiThreadTest
    fun testMarkTintMode() {
        val markTintMode = view.rx_checkMarkTintMode

        Observable.just(PorterDuff.Mode.SRC).bindTo(markTintMode)
        assertThat(view.checkMarkTintMode, equalTo(PorterDuff.Mode.SRC))

        markTintMode.bindTo(Observable.just(PorterDuff.Mode.SRC_IN))
        assertThat(view.checkMarkTintMode, equalTo(PorterDuff.Mode.SRC_IN))
    }

    @Test
    @UiThreadTest
    fun testDrawable() {
        val drawable = view.rx_checkMarkDrawable

        Observable.just(ContextCompat.getDrawable(context, R.drawable.ic_account_balance_wallet_black_18dp)).bindTo(drawable)
        assertThat(view.checkMarkDrawable, withDrawable(context, R.drawable.ic_account_balance_wallet_black_18dp))

        drawable.bindTo(Observable.just(ContextCompat.getDrawable(context, R.drawable.ic_accessibility_black_18dp)))
        assertThat(view.checkMarkDrawable, withDrawable(context, R.drawable.ic_accessibility_black_18dp))
    }

}


