package com.github.kittinunf.reactiveandroid.view

import android.app.Activity
import android.view.View
import com.github.kittinunf.reactiveandroid.ui.BuildConfig
import org.junit.Assert.assertThat
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricGradleTestRunner
import org.robolectric.annotation.Config
import rx.Observable
import org.hamcrest.CoreMatchers.`is` as isEqualTo

@RunWith(RobolectricGradleTestRunner::class)
@Config(constants = BuildConfig::class, sdk = intArrayOf(21))
class ViewPropertyTest {

    val activity = Activity()
    val view = View(activity)

    @Test
    fun activated() {
        var result = false

        view.isActivated = true
        view.rx_activated.subscribe {
            result = it
        }

        assertThat(result, isEqualTo(true))
    }

    @Test
    fun alpha() {
        var result = -1f

        val alpha = view.rx_alpha

        alpha.subscribe {
            result = it
        }

        assertThat(result, isEqualTo(1f))

        val o = Observable.just(0f)
        alpha.bindTo(o)

        assertThat(result, isEqualTo(0f))
    }

    @Test
    fun background() {

    }

}