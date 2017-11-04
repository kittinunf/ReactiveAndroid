package com.github.kittinunf.reactiveandroid.widget

import android.annotation.TargetApi
import android.os.Build
import android.support.test.InstrumentationRegistry
import android.support.test.annotation.UiThreadTest
import android.support.test.filters.SdkSuppress
import android.support.test.rule.UiThreadTestRule
import android.support.test.runner.AndroidJUnit4
import android.view.Menu
import android.view.MenuItem
import android.widget.ActionMenuView
import io.reactivex.observers.TestObserver
import org.hamcrest.CoreMatchers.equalTo
import org.junit.Assert.assertThat
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@TargetApi(Build.VERSION_CODES.M)
@SdkSuppress(minSdkVersion = Build.VERSION_CODES.M)
@RunWith(AndroidJUnit4::class)
class ActionMenuViewEventTest {

    @Rule
    @JvmField
    val uiThreadTestRule = UiThreadTestRule()

    private val context = InstrumentationRegistry.getContext()
    private val view = ActionMenuView(context)
    private val menu = view.menu

    @Test
    @UiThreadTest
    fun testMenuItemClick() {
        // add menu items
        val item1 = menu.add(0, 1000, Menu.NONE, "menu_1000")
        val item2 = menu.add(0, 1001, Menu.NONE, "menu_1001")

        val t = TestObserver<MenuItem>()
        val s = view.rx_menuItemClick(true).subscribeWith(t)

        t.assertNoValues()
        t.assertNoErrors()

        menu.performIdentifierAction(1000, 0)
        t.assertValueCount(1)
        val first = t.values().first()
        assertThat(first, equalTo(item1))

        menu.performIdentifierAction(1001, 0)
        t.assertValueCount(2)
        val second = t.values()[1]
        assertThat(second, equalTo(item2))

        s.dispose()

        menu.performIdentifierAction(1000, 0)
        t.assertValueCount(2)
    }

}
