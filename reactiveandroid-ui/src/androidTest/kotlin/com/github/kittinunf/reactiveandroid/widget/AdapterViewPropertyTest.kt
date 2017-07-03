package com.github.kittinunf.reactiveandroid.widget

import android.support.test.InstrumentationRegistry
import android.support.test.annotation.UiThreadTest
import android.support.test.rule.ActivityTestRule
import android.support.test.runner.AndroidJUnit4
import android.view.View
import android.widget.TextView
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
class AdapterViewPropertyTest {

    @Rule
    @JvmField
    val activityRule = ActivityTestRule(AdapterViewTestActivity::class.java)

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
    fun testEmptyView() {
        val listView = activityRule.activity.listView

        val textEmptyView = TextView(context)
        val emptyView = listView.rx_emptyView
        Observable.just(textEmptyView).bindTo(emptyView)

        assertThat(textEmptyView.visibility, equalTo(View.GONE))

        activityRule.activity.clear()
        assertThat(textEmptyView.visibility, equalTo(View.VISIBLE))
    }

    @Test
    @UiThreadTest
    fun testSelection() {
        val spinner = activityRule.activity.listView

        val selection = spinner.rx_selection
        Observable.just(4).bindTo(selection)

        assertThat(spinner.selectedItem as String, equalTo("5"))

        selection.bindTo(Observable.just(1))

        assertThat(spinner.selectedItem as String, equalTo("2"))
    }

}
