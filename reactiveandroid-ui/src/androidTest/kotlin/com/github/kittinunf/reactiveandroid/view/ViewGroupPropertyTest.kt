package com.github.kittinunf.reactiveandroid.view

import android.annotation.TargetApi
import android.os.Build
import android.support.test.InstrumentationRegistry
import android.support.test.annotation.UiThreadTest
import android.support.test.filters.SdkSuppress
import android.support.test.rule.UiThreadTestRule
import android.support.test.runner.AndroidJUnit4
import android.view.ViewGroup
import android.view.animation.LayoutAnimationController
import android.view.animation.TranslateAnimation
import android.widget.LinearLayout
import com.github.kittinunf.reactiveandroid.reactive.bindTo
import com.github.kittinunf.reactiveandroid.scheduler.AndroidThreadScheduler
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers
import org.hamcrest.CoreMatchers.equalTo
import org.junit.Assert.assertThat
import org.junit.BeforeClass
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class ViewGroupPropertyTest {

    @Rule
    @JvmField
    val uiThreadTestRule = UiThreadTestRule()

    private val context = InstrumentationRegistry.getContext()
    private val instrument = InstrumentationRegistry.getInstrumentation()
    private val view = LinearLayout(context)

    companion object {
        @BeforeClass
        @JvmStatic
        fun setUp() {
            AndroidThreadScheduler.main = Schedulers.trampoline()
        }
    }

    @Test
    @UiThreadTest
    fun testLayoutAnimation() {
        val layout = view.rx_layoutAnimation

        val layoutAnim = LayoutAnimationController(TranslateAnimation(0.0f, 1.0f, 0.0f, 1.0f))

        layout.bindTo(Observable.just(layoutAnim))
        assertThat(view.layoutAnimation, equalTo(layoutAnim))
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR2)
    @SdkSuppress(minSdkVersion = Build.VERSION_CODES.JELLY_BEAN_MR2)
    @Test
    @UiThreadTest
    fun testClipChildren() {
        val clip = view.rx_clipChildren

        clip.bindTo(Observable.just(true))
        assertThat(view.clipChildren, equalTo(true))

        Observable.just(false).bindTo(clip)
        assertThat(view.clipChildren, equalTo(false))
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @SdkSuppress(minSdkVersion = Build.VERSION_CODES.LOLLIPOP)
    @Test
    @UiThreadTest
    fun testClipToPadding() {
        val clip = view.rx_clipToPadding

        clip.bindTo(Observable.just(true))
        assertThat(view.clipToPadding, equalTo(true))

        Observable.just(false).bindTo(clip)
        assertThat(view.clipToPadding, equalTo(false))
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR2)
    @SdkSuppress(minSdkVersion = Build.VERSION_CODES.JELLY_BEAN_MR2)
    @Test
    @UiThreadTest
    fun testLayoutMode() {
        val layout = view.rx_layoutMode

        layout.bindTo(Observable.just(ViewGroup.LAYOUT_MODE_OPTICAL_BOUNDS))
        assertThat(view.layoutMode, equalTo(ViewGroup.LAYOUT_MODE_OPTICAL_BOUNDS))

        Observable.just(ViewGroup.LAYOUT_MODE_OPTICAL_BOUNDS).bindTo(layout)
        assertThat(view.layoutMode, equalTo(ViewGroup.LAYOUT_MODE_OPTICAL_BOUNDS))
    }

    @Test
    @UiThreadTest
    fun testLayoutTransition() {
    }

}
