package com.github.kittinunf.reactiveandroid.widget

import android.support.test.InstrumentationRegistry
import android.support.test.annotation.UiThreadTest
import android.support.test.rule.ActivityTestRule
import android.support.test.runner.AndroidJUnit4
import android.view.MenuItem
import android.widget.PopupMenu
import org.hamcrest.CoreMatchers.equalTo
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import rx.observers.TestSubscriber

@RunWith(AndroidJUnit4::class)
class PopupMenuEventTest {

    @Rule
    @JvmField
    val activityRule = ActivityTestRule(PopupMenuTestActivity::class.java)

    val instrumentation = InstrumentationRegistry.getInstrumentation()

    @Test
    @UiThreadTest
    fun testMenuItemClick() {
        val popupMenu = activityRule.activity.popupMenu
        val menu = popupMenu.menu
        menu.add(0, 1000, 0, "One")
        menu.add(0, 1001, 0, "Two")

        val t = TestSubscriber<MenuItem>()

        val s = popupMenu.rx_menuItemClick(true).subscribe(t)

        t.assertNoErrors()
        t.assertNoValues()

        popupMenu.show()
        menu.performIdentifierAction(1001, 0)
        val first = t.onNextEvents.first()
        assertThat(first.itemId, equalTo(1001))

        menu.performIdentifierAction(1000, 0)
        val second = t.onNextEvents[1]
        assertThat(second.itemId, equalTo(1000))

        s.unsubscribe()

        menu.performIdentifierAction(1000, 0)

        t.assertValueCount(2)
    }

    @Test
    @UiThreadTest
    fun testDismiss() {
        val popupMenu = activityRule.activity.popupMenu
        val menu = popupMenu.menu

        menu.add(0, 1000, 0, "One")
        menu.add(0, 1001, 0, "Two")

        val t = TestSubscriber<PopupMenu>()

        val s = popupMenu.rx_dismiss().subscribe(t)

        t.assertNoErrors()
        t.assertNoValues()

        popupMenu.show()
        popupMenu.dismiss()

        val first = t.onNextEvents.first()
        assertThat(first, equalTo(popupMenu))
        t.assertValueCount(1)

        s.unsubscribe()

        popupMenu.show()
        popupMenu.dismiss()
        t.assertValueCount(1)
    }

}