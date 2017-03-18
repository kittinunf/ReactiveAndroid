package com.github.kittinunf.reactiveandroid.widget

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import android.support.test.InstrumentationRegistry
import android.support.test.annotation.UiThreadTest
import android.support.test.rule.UiThreadTestRule
import android.support.test.runner.AndroidJUnit4
import android.widget.ProgressBar
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
class ProgressBarPropertyTest {

    @Rule
    @JvmField
    val uiThreadTestRule = UiThreadTestRule()

    private val context = InstrumentationRegistry.getContext()
    private val instrument = InstrumentationRegistry.getInstrumentation()
    private val view = ProgressBar(context, null, 0)

    companion object {
        @BeforeClass
        @JvmStatic
        fun setUp() {
            AndroidThreadScheduler.main = Schedulers.immediate()
        }
    }

    @Test
    @UiThreadTest
    fun testIndeterminateDrawable() {
        val drawable = view.rx_indeterminateDrawable

        val colorDrawable = ColorDrawable(Color.BLACK)
        Observable.just(colorDrawable).bindTo(drawable)
        assertThat(view.indeterminateDrawable, withColorDrawable(context, android.R.color.black))

        val anotherColorDrawable = ColorDrawable(Color.WHITE)
        val anotherDrawable = Observable.just(anotherColorDrawable) as Observable<Drawable>
        drawable.bindTo(anotherDrawable)
        assertThat(view.indeterminateDrawable, withColorDrawable(context, android.R.color.white))
    }

    @Test
    @UiThreadTest
    fun testMax() {
        val max = view.rx_max

        Observable.just(5).bindTo(max)
        assertThat(view.max, equalTo(5))

        max.bindTo(Observable.just(10))
        assertThat(view.max, equalTo(10))
    }

    @Test
    @UiThreadTest
    fun testProgress() {
        val progress = view.rx_progress
        view.isIndeterminate = false

        Observable.just(10).bindTo(progress)
        assertThat(view.progress, equalTo(10))

        progress.bindTo(Observable.just(20))
        assertThat(view.progress, equalTo(20))
    }

    @Test
    @UiThreadTest
    fun testProgressDrawable() {
        val progressDrawable = view.rx_progressDrawable

        val colorDrawable = ColorDrawable(Color.BLACK)
        Observable.just(colorDrawable).bindTo(progressDrawable)
        assertThat(view.progressDrawable, withColorDrawable(context, android.R.color.black))

        val anotherColorDrawable = ColorDrawable(Color.WHITE)
        val anotherDrawable = Observable.just(anotherColorDrawable) as Observable<Drawable>
        progressDrawable.bindTo(anotherDrawable)
        assertThat(view.progressDrawable, withColorDrawable(context, android.R.color.white))
    }

    @Test
    @UiThreadTest
    fun testSecondaryProgress() {
        val progress = view.rx_secondaryProgress
        view.max = 20
        view.isIndeterminate = false

        Observable.just(10).bindTo(progress)
        assertThat(view.secondaryProgress, equalTo(10))

        progress.bindTo(Observable.just(20))
        assertThat(view.secondaryProgress, equalTo(20))
    }

}
