package com.github.kittinunf.reactiveandroid.widget

import android.support.test.InstrumentationRegistry
import android.support.test.annotation.UiThreadTest
import android.support.test.rule.ActivityTestRule
import android.support.test.runner.AndroidJUnit4
import android.view.View
import android.widget.SearchView
import com.github.kittinunf.reactiveandroid.reactive.view.FocusChangeListener
import io.reactivex.observers.TestObserver
import org.hamcrest.CoreMatchers.equalTo
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class SearchViewEventTest {

    @Rule
    @JvmField
    val activityRule = ActivityTestRule(SearchViewTestActivity::class.java)

    val instrumentation = InstrumentationRegistry.getInstrumentation()

    @Test
    @UiThreadTest
    fun testClose() {
        val view = activityRule.activity.searchView

        view.setQuery("abc", true)

        val t = TestObserver<Unit>()
        val s = view.rx_close(false).subscribeWith(t)

        t.assertNoValues()
        t.assertNoErrors()

        view.setQuery("", false)
        view.isIconified = true

        val first = t.values()[0]
        assertThat(first, equalTo(Unit))
        t.assertValueCount(1)

        view.setQuery("def", true)

        view.setQuery("", false)
        view.isIconified = true
        val second = t.values()[1]

        assertThat(second, equalTo(Unit))
        t.assertValueCount(2)

        s.dispose()

        view.setQuery("def", true)

        view.setQuery("", false)
        view.isIconified = true

        t.assertValueCount(2)
    }

    @Test
    @UiThreadTest
    fun testQueryTextFocusChange() {
        val view = activityRule.activity.searchView

        val t = TestObserver<FocusChangeListener>()
        val s = view.rx_queryTextFocusChange().subscribeWith(t)

        t.assertNoErrors()

        view.setQuery("abc", true)
        view.isIconified = false

        val first = t.values()[0]
        assertThat(first.hasFocus, equalTo(true))
        t.assertValueCount(1)

        view.setQuery("", false)
        view.isIconified = true

        val second = t.values()[1]
        assertThat(second.hasFocus, equalTo(false))
        t.assertValueCount(2)

        s.dispose()
    }

    @Test
    @UiThreadTest
    fun testQueryTextChange() {
        val view = activityRule.activity.searchView

        val t = TestObserver<String>()
        val s = view.rx_queryTextChange(true).subscribeWith(t)

        view.setQuery("h", false)

        val first = t.values()[0]
        assertThat(first, equalTo("h"))
        t.assertValueCount(1)

        view.setQuery("he", false)

        val second = t.values()[1]
        assertThat(second, equalTo("he"))
        t.assertValueCount(2)

        view.setQuery("hel", false)
        val third = t.values()[2]
        assertThat(third, equalTo("hel"))
        t.assertValueCount(3)

        s.dispose()

        view.setQuery("hell", false)
        view.setQuery("hello", false)
        t.assertValueCount(3)
    }

    @Test
    @UiThreadTest
    fun testQueryTextSubmit() {
        val view = activityRule.activity.searchView

        val t = TestObserver<String>()
        val s = view.rx_queryTextSubmit(true).subscribeWith(t)

        view.setQuery("hello", true)

        val first = t.values()[0]
        assertThat(first, equalTo("hello"))
        t.assertValueCount(1)

        view.setQuery("world", true)

        val second = t.values()[1]
        assertThat(second, equalTo("world"))
        t.assertValueCount(2)

        s.dispose()

        view.setQuery("oh yeah!", true)
        t.assertValueCount(2)
    }

    @Test
    @UiThreadTest
    fun testSearchClick() {
        val view = activityRule.activity.searchView

        val t = TestObserver<View>()
        val s = view.rx_searchClick().subscribeWith(t)

        view.setQuery("hello", false)
        view.isIconified = false

        val first = t.values()[0]
        assertThat((first as SearchView).query.toString(), equalTo("hello"))
        t.assertValueCount(1)

        view.setQuery("world", false)
        view.isIconified = false

        val second = t.values()[1]
        assertThat((second as SearchView).query.toString(), equalTo("world"))
        t.assertValueCount(2)

        s.dispose()

        view.setQuery("oh yeah!", false)
        view.isIconified = false

        t.assertValueCount(2)
    }

}
