package com.github.kittinunf.reactiveandroid.view

import android.app.Activity
import android.view.View
import com.github.kittinunf.reactiveandroid.ui.BuildConfig
import org.junit.Assert.assertThat
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.Robolectric
import org.robolectric.RobolectricGradleTestRunner
import org.robolectric.annotation.Config
import org.hamcrest.CoreMatchers.`is` as isEqualTo

@RunWith(RobolectricGradleTestRunner::class)
@Config(constants = BuildConfig::class, sdk = intArrayOf(21))
class ViewEventTest {

    val activity = Robolectric.setupActivity(Activity::class.java)
    val view = View(activity)

    @Test
    fun click() {
        var result = false

        view.rx_click().subscribe {
            result = true
        }

        view.performClick()

        assertThat(result, isEqualTo(true))
    }

}
