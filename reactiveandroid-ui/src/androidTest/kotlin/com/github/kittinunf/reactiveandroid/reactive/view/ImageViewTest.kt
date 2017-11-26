package com.github.kittinunf.reactiveandroid.reactive.view

import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.support.test.InstrumentationRegistry
import android.support.test.annotation.UiThreadTest
import android.support.test.runner.AndroidJUnit4
import android.support.v4.content.ContextCompat
import android.widget.ImageView
import com.github.kittinunf.reactiveandroid.ui.test.R
import io.reactivex.subjects.BehaviorSubject
import org.awaitility.Awaitility.await
import org.hamcrest.CoreMatchers.equalTo
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class ImageViewTest {

    val context = InstrumentationRegistry.getContext()

    lateinit var view: ImageView

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

    @Test
    @UiThreadTest
    fun bitmap() {
        val subject = BehaviorSubject.create<Bitmap>()
        subject.subscribe(view.rx.bitmap)

        var drawable = ContextCompat.getDrawable(context, R.drawable.ic_accessibility_black_18dp) as BitmapDrawable
        subject.onNext(drawable.bitmap)
        await().untilAsserted {
            assertThat(view.drawable, withDrawable(context, R.drawable.ic_accessibility_black_18dp))
        }

        drawable = ContextCompat.getDrawable(context, R.drawable.ic_account_balance_wallet_black_18dp) as BitmapDrawable
        subject.onNext(drawable.bitmap)
        await().untilAsserted {
            assertThat(view.drawable, withDrawable(context, R.drawable.ic_account_balance_wallet_black_18dp))
        }
    }

    @Test
    @UiThreadTest
    fun resourceId() {
        val subject = BehaviorSubject.create<Int>()
        subject.subscribe(view.rx.resourceId)

        subject.onNext(R.drawable.ic_accessibility_black_18dp)
        await().untilAsserted {
            assertThat(view.drawable, withDrawable(context, R.drawable.ic_accessibility_black_18dp))
        }

        subject.onNext(R.drawable.ic_account_balance_wallet_black_18dp)
        await().untilAsserted {
            assertThat(view.drawable, withDrawable(context, R.drawable.ic_account_balance_wallet_black_18dp))
        }
    }

    @Test
    @UiThreadTest
    fun scaleType() {
        val subject = BehaviorSubject.create<ImageView.ScaleType>()
        subject.subscribe(view.rx.scaleType)

        subject.onNext(ImageView.ScaleType.CENTER)
        await().untilAsserted {
            assertThat(view.scaleType, equalTo(ImageView.ScaleType.CENTER))
        }

        subject.onNext(ImageView.ScaleType.FIT_XY)
        await().untilAsserted {
            assertThat(view.scaleType, equalTo(ImageView.ScaleType.FIT_XY))
        }
    }

}