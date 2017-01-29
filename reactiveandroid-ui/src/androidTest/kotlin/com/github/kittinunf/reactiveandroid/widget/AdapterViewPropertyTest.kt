package com.github.kittinunf.reactiveandroid.widget

import android.support.test.InstrumentationRegistry
import android.support.test.annotation.UiThreadTest
import android.support.test.rule.UiThreadTestRule
import android.support.test.runner.AndroidJUnit4
import android.view.View
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.Spinner
import android.widget.TextView
import com.github.kittinunf.reactiveandroid.rx.bindTo
import com.github.kittinunf.reactiveandroid.scheduler.AndroidThreadScheduler
import org.hamcrest.CoreMatchers.equalTo
import org.hamcrest.MatcherAssert.assertThat
import org.junit.BeforeClass
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import rx.Observable
import rx.schedulers.Schedulers


@RunWith(AndroidJUnit4::class)
class AdapterViewPropertyTest {
    @Rule
    @JvmField
    val uiThreadTestRule = UiThreadTestRule()

    private val context = InstrumentationRegistry.getContext()
    private val instrument = InstrumentationRegistry.getInstrumentation()

    private val items = mutableListOf("1", "2", "3", "4", "5")
    private val adapter = ArrayAdapter(context, android.R.layout.simple_list_item_1, items)
    private val spinner = Spinner(context)
    private val listView = ListView(context)

    companion object {
        @BeforeClass
        @JvmStatic
        fun setUp() {
            AndroidThreadScheduler.main = Schedulers.immediate()
        }
    }

    @Test
    @UiThreadTest
    fun testEmptyView() {
        listView.adapter = adapter

        val textEmptyView = TextView(context)
        val emptyView = listView.rx_emptyView
        Observable.just(textEmptyView).bindTo(emptyView)

        assertThat(textEmptyView.visibility, equalTo(View.GONE))

        items.clear()
        adapter.notifyDataSetChanged()
        assertThat(textEmptyView.visibility, equalTo(View.VISIBLE))
    }

    @Test
    @UiThreadTest
    fun testSelection() {
        spinner.adapter = adapter

        val selection = spinner.rx_selection
        Observable.just(4).bindTo(selection)

        assertThat(spinner.selectedItem as String, equalTo("5"))

        selection.bindTo(Observable.just(1))

        assertThat(spinner.selectedItem as String, equalTo("2"))
    }

}