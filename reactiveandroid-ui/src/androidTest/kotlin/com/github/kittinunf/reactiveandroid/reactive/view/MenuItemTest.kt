package com.github.kittinunf.reactiveandroid.reactive.view

import android.support.test.InstrumentationRegistry
import android.support.test.runner.AndroidJUnit4
import android.view.MenuItem
import org.junit.Before
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class MenuItemTest {

    val context = InstrumentationRegistry.getContext()

    lateinit var view: MenuItem

    @Before
    fun before() {
        view = TestMenuItem(context)
    }

}