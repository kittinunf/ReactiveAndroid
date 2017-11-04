package com.github.kittinunf.reactiveandroid.view

import android.support.test.InstrumentationRegistry
import android.support.test.annotation.UiThreadTest
import android.support.test.rule.UiThreadTestRule
import android.support.test.runner.AndroidJUnit4
import android.view.MenuItem
import io.reactivex.observers.TestObserver
import org.hamcrest.CoreMatchers.equalTo
import org.junit.Assert.assertThat
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class MenuItemEventTest {

    @Rule
    @JvmField
    val uiThreadTestRule = UiThreadTestRule()

    private val context = InstrumentationRegistry.getContext()

    private val menuItem = TestMenuItem(context)

    @Test
    @UiThreadTest
    fun testClick() {
        val t = TestObserver<MenuItem>()
        val s = menuItem.rx_menuItemClick(true).subscribeWith(t)

        t.assertNoErrors()
        t.assertNoValues()

        menuItem.performClick()

        t.assertValueCount(1)
        val item = t.values().first()
        assertThat(item, equalTo(menuItem as MenuItem))

        s.dispose()

        menuItem.performClick()
        t.assertValueCount(1)
    }

    @Test
    @UiThreadTest
    fun testExpandedCollapsed() {
        val t1 = TestObserver<MenuItem>()
        val t2 = TestObserver<MenuItem>()

        val s1 = menuItem.rx_actionExpand(true).subscribeWith(t1)
        val s2 = menuItem.rx_actionCollapse(true).subscribeWith(t2)

        t1.assertNoErrors()
        t1.assertNoValues()
        t2.assertNoErrors()
        t2.assertNoValues()

        menuItem.expandActionView()

        t1.assertValueCount(1)
        t2.assertValueCount(0)

        val item = t1.values().first()
        assertThat(item, equalTo(menuItem as MenuItem))

        menuItem.collapseActionView()
        t1.assertValueCount(1)
        t2.assertValueCount(1)

        val anotherItem = t2.values().first()
        assertThat(anotherItem, equalTo(menuItem as MenuItem))

        s1.dispose()

        menuItem.expandActionView()
        t1.assertValueCount(1)

        s2.dispose()

        menuItem.collapseActionView()
        t2.assertValueCount(1)
    }

}
