package com.github.kittinunf.reactiveandroid.view

import android.annotation.TargetApi
import android.content.res.ColorStateList
import android.graphics.Color
import android.graphics.PorterDuff
import android.os.Build
import android.support.test.InstrumentationRegistry
import android.support.test.annotation.UiThreadTest
import android.support.test.filters.SdkSuppress
import android.support.test.rule.UiThreadTestRule
import android.support.test.runner.AndroidJUnit4
import android.support.v4.content.ContextCompat
import android.widget.ImageView
import com.github.kittinunf.reactiveandroid.rx.bindTo
import com.github.kittinunf.reactiveandroid.scheduler.AndroidThreadScheduler
import com.github.kittinunf.reactiveandroid.ui.test.R
import org.hamcrest.CoreMatchers.equalTo
import org.junit.Assert.assertThat
import org.junit.BeforeClass
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import rx.Observable
import rx.schedulers.Schedulers

@RunWith(AndroidJUnit4::class)
class ImageViewPropertyTest {

    @Rule
    @JvmField
    val uiThreadTestRule = UiThreadTestRule()

    private val context = InstrumentationRegistry.getContext()

    private val view = ImageView(context)

    companion object {
        @BeforeClass
        @JvmStatic
        fun setUp() {
            AndroidThreadScheduler.main = Schedulers.immediate()
        }
    }

    @Test
    @UiThreadTest
    fun testBaseline() {
        val baseline = view.rx_baseline

        Observable.just(1).bindTo(baseline)
        assertThat(view.baseline, equalTo(1))

        baseline.bindTo(Observable.just(2))
        assertThat(view.baseline, equalTo(2))
    }

    @Test
    @UiThreadTest
    fun testDrawable() {
        val drawable = view.rx_drawable

        val image = ContextCompat.getDrawable(context, R.drawable.ic_accessibility_black_18dp)
        Observable.just(image).bindTo(drawable)
        assertThat(view.drawable, equalTo(image))

        val anotherImage = ContextCompat.getDrawable(context, R.drawable.ic_account_balance_wallet_black_18dp)
        drawable.bindTo(Observable.just(anotherImage))
        assertThat(view.drawable, equalTo(anotherImage))
    }

    @Test
    @UiThreadTest
    fun testImageAlpha() {
        val imageAlpha = view.rx_imageAlpha

        Observable.just(50).bindTo(imageAlpha)
        assertThat(view.imageAlpha, equalTo(50))

        imageAlpha.bindTo(Observable.just(100))
        assertThat(view.imageAlpha, equalTo(100))
    }

    @Test
    @UiThreadTest
    fun testImageLevel() {
        view.setImageResource(R.drawable.ic_accessibility_black_18dp)
        val imageLevel = view.rx_imageLevel

        Observable.just(1).bindTo(imageLevel)
        assertThat(view.drawable.level, equalTo(1))

        imageLevel.bindTo(Observable.just(9999))
        assertThat(view.drawable.level, equalTo(9999))
    }

    @Test
    @UiThreadTest
    fun testImageMatrix() {
    }

    @Test
    @UiThreadTest
    fun testAdjustViewBounds() {
        val adjustViewBounds = view.rx_adjustViewBounds

        Observable.just(true).bindTo(adjustViewBounds)
        assertThat(view.adjustViewBounds, equalTo(true))

        adjustViewBounds.bindTo(Observable.just(false))
        assertThat(view.adjustViewBounds, equalTo(false))
    }

    @Test
    @UiThreadTest
    fun testCropToPadding() {
        val cropToPadding = view.rx_cropToPadding

        Observable.just(true).bindTo(cropToPadding)
        assertThat(view.cropToPadding, equalTo(true))

        cropToPadding.bindTo(Observable.just(false))
        assertThat(view.cropToPadding, equalTo(false))
    }

    @Test
    @UiThreadTest
    fun testMaxWidth() {
        val maxWidth = view.rx_maxWidth

        Observable.just(100).bindTo(maxWidth)
        assertThat(view.maxWidth, equalTo(100))

        maxWidth.bindTo(Observable.just(200))
        assertThat(view.maxWidth, equalTo(200))
    }

    @Test
    @UiThreadTest
    fun testMaxHeight() {
        val maxWidth = view.rx_maxHeight

        Observable.just(100).bindTo(maxWidth)
        assertThat(view.maxHeight, equalTo(100))

        maxWidth.bindTo(Observable.just(200))
        assertThat(view.maxHeight, equalTo(200))
    }

    @Test
    @UiThreadTest
    fun testScaleType() {
        val scaleType = view.rx_scaleType

        Observable.just(ImageView.ScaleType.CENTER).bindTo(scaleType)
        assertThat(view.scaleType, equalTo(ImageView.ScaleType.CENTER))

        scaleType.bindTo(Observable.just(ImageView.ScaleType.FIT_XY))
        assertThat(view.scaleType, equalTo(ImageView.ScaleType.FIT_XY))
    }

    @TargetApi(Build.VERSION_CODES.M)
    @SdkSuppress(minSdkVersion = Build.VERSION_CODES.M)
    @Test
    @UiThreadTest
    fun testImageTintMode() {
        val tintMode = view.rx_imageTintMode

        Observable.just(PorterDuff.Mode.LIGHTEN).bindTo(tintMode)
        assertThat(view.imageTintMode, equalTo(PorterDuff.Mode.LIGHTEN))

        tintMode.bindTo(Observable.just(PorterDuff.Mode.DARKEN))
        assertThat(view.imageTintMode, equalTo(PorterDuff.Mode.DARKEN))
    }

    @TargetApi(Build.VERSION_CODES.M)
    @SdkSuppress(minSdkVersion = Build.VERSION_CODES.M)
    @Test
    @UiThreadTest
    fun testImageTintList() {
        val imageTintList = view.rx_imageTintList

        val colorStateList = ColorStateList.valueOf(Color.RED)

        Observable.just(colorStateList).bindTo(imageTintList)
        assertThat(view.imageTintList, equalTo(colorStateList))

        val anotherColorStateList = ColorStateList.valueOf(Color.BLACK)
        imageTintList.bindTo(Observable.just(anotherColorStateList))
        assertThat(view.imageTintList, equalTo(anotherColorStateList))
    }

}