package com.github.kittinunf.reactiveandroid.widget

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
class SearchViewPropertyTest {

    @Rule
    @JvmField
    val activityRule = ActivityTestRule(SearchViewTestActivity::class.java)

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
    fun testQuery() {
        val view = activityRule.activity.searchView
        val query = view.rx_query

        Observable.just(SearchViewQuery("hello", false)).bindTo(query)
        assertThat(view.query.toString(), equalTo("hello"))

        query.bindTo(Observable.just(SearchViewQuery("world", true)))
        assertThat(view.query.toString(), equalTo("world"))
    }

    @Test
    @UiThreadTest
    fun testSubmitButtonEnabled() {
        val view = activityRule.activity.searchView
        val enabled = view.rx_submitButtonEnabled

        Observable.just(false).bindTo(enabled)
        assertThat(view.isSubmitButtonEnabled, equalTo(false))

        enabled.bindTo(Observable.just(true))
        assertThat(view.isSubmitButtonEnabled, equalTo(true))
    }

}
