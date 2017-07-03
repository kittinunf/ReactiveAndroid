package com.github.kittinunf.reactiveandroid.widget

import android.support.test.InstrumentationRegistry
import android.support.test.annotation.UiThreadTest
import android.support.test.rule.UiThreadTestRule
import android.support.test.runner.AndroidJUnit4
import android.widget.RadioButton
import android.widget.RadioGroup
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
class RadioGroupPropertyTest {

    @Rule
    @JvmField
    val uiThreadTestRule = UiThreadTestRule()

    private val context = InstrumentationRegistry.getContext()
    private val instrument = InstrumentationRegistry.getInstrumentation()
    private val view = RadioGroup(context)

    companion object {
        @BeforeClass
        @JvmStatic
        fun setUp() {
            AndroidThreadScheduler.main = Schedulers.trampoline()
        }
    }

    @Test
    @UiThreadTest
    fun testCheck() {
        val radio1 = RadioButton(context)
        radio1.id = 1001

        val radio2 = RadioButton(context)
        radio2.id = 1002

        val radio3 = RadioButton(context)
        radio3.id = 1003

        view.addView(radio1)
        view.addView(radio2)
        view.addView(radio3)

        val check = view.rx_check

        Observable.just(1003).bindTo(check)
        assertThat(view.checkedRadioButtonId, equalTo(1003))

        check.bindTo(Observable.just(1001))
        assertThat(view.checkedRadioButtonId, equalTo(1001))
    }

}
