package com.github.kittinunf.reactiveandroid.reactive.view

import android.graphics.drawable.Drawable
import android.support.test.InstrumentationRegistry
import android.support.test.annotation.UiThreadTest
import android.support.test.runner.AndroidJUnit4
import android.support.v4.content.ContextCompat
import android.widget.ImageView
import com.github.kittinunf.reactiveandroid.scheduler.AndroidThreadScheduler
import com.github.kittinunf.reactiveandroid.ui.test.R
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.BehaviorSubject
import org.awaitility.Awaitility.await
import org.hamcrest.CoreMatchers.equalTo
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Before
import org.junit.BeforeClass
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class ImageViewTest {

    val context = InstrumentationRegistry.getContext()

    lateinit var view: ImageView

    companion object {
        @BeforeClass
        @JvmStatic
        fun setUp() {
            AndroidThreadScheduler.main = Schedulers.trampoline()
        }
    }

    @Before
    fun before() {
        view = ImageView(context)
    }

    @Test
    @UiThreadTest
    fun drawable() {
        val subject = BehaviorSubject.create<Drawable>()
        subject.subscribe(view.rx.drawable)

        var drawable = ContextCompat.getDrawable(context, R.drawable.ic_accessibility_black_18dp)
        subject.onNext(drawable)
        await().untilAsserted { assertThat(view.drawable, equalTo(drawable)) }

        drawable = ContextCompat.getDrawable(context, R.drawable.ic_account_balance_wallet_black_18dp)
        subject.onNext(drawable)
        await().untilAsserted { assertThat(view.drawable, equalTo(drawable)) }
    }

}