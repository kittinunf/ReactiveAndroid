package com.github.kittinunf.reactiveandroid.widget

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import android.support.test.InstrumentationRegistry
import android.support.test.annotation.UiThreadTest
import android.support.test.rule.ActivityTestRule
import android.support.test.runner.AndroidJUnit4
import android.support.v4.content.ContextCompat
import android.widget.AutoCompleteTextView
import com.github.kittinunf.reactiveandroid.reactive.bindTo
import com.github.kittinunf.reactiveandroid.scheduler.AndroidThreadScheduler
import com.github.kittinunf.reactiveandroid.ui.test.R
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers
import org.hamcrest.Description
import org.hamcrest.Matcher
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.TypeSafeMatcher
import org.junit.BeforeClass
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class AutoCompleteTextViewPropertyTest {

    @Rule
    @JvmField
    val activityRule = ActivityTestRule(AutoCompleteTextViewTestActivity::class.java)

    val instrumentation = InstrumentationRegistry.getInstrumentation()

    private val context = InstrumentationRegistry.getContext()

    companion object {
        @BeforeClass
        @JvmStatic
        fun setUp() {
            AndroidThreadScheduler.main = Schedulers.trampoline()
        }
    }

    @Test
    @UiThreadTest
    fun testDropdownBackground() {
        val textView = activityRule.activity.textView
        val background = textView.rx_dropdownBackground

        Observable.just(ContextCompat.getDrawable(context, R.drawable.ic_accessibility_black_18dp))
                .bindTo(background)

        val colorDrawable = ColorDrawable(Color.BLACK)
        Observable.just(colorDrawable).bindTo(background)
        assertThat(textView.dropDownBackground, withColorDrawable(context, android.R.color.black))

        val anotherColorDrawable = ColorDrawable(Color.WHITE)
        val anotherDrawable = Observable.just(anotherColorDrawable) as Observable<Drawable>
        background.bindTo(anotherDrawable)
        assertThat(textView.dropDownBackground, withColorDrawable(context, android.R.color.white))
    }

    @Test
    @UiThreadTest
    fun testValidator() {
        val upperCaseValidator = object : AutoCompleteTextView.Validator {
            override fun fixText(invalidText: CharSequence?): CharSequence {
                return invalidText.toString().toUpperCase()
            }

            override fun isValid(text: CharSequence?): Boolean {
                return true
            }
        }

        val lowerCaseValidator = object : AutoCompleteTextView.Validator {
            override fun fixText(invalidText: CharSequence?): CharSequence {
                return invalidText.toString().toLowerCase()
            }

            override fun isValid(text: CharSequence?): Boolean {
                return true
            }
        }

        val textView = activityRule.activity.textView

        val validator = textView.rx_validator

        Observable.just(upperCaseValidator).bindTo(validator)
        assertThat(textView.validator, withValidator(upperCaseValidator))

        validator.bindTo(Observable.just(lowerCaseValidator) as Observable<AutoCompleteTextView.Validator>)
        assertThat(textView.validator, withValidator(lowerCaseValidator))
    }

}

fun withColorDrawable(context: Context, resId: Int): Matcher<Drawable> = object : TypeSafeMatcher<Drawable>() {

    override fun matchesSafely(drawable: Drawable?): Boolean {
        if (drawable == null) return false

        val otherColor = ContextCompat.getColor(context, resId)

        val colorValue = (drawable as ColorDrawable).color
        val otherColorValue = otherColor
        return colorValue == otherColorValue
    }

    override fun describeTo(description: Description?) {
        description?.appendText("view that has color resource id: $resId has color that doesn't match")
    }

}

fun withValidator(validator: AutoCompleteTextView.Validator): Matcher<AutoCompleteTextView.Validator> = object : TypeSafeMatcher<AutoCompleteTextView.Validator>() {

    override fun matchesSafely(item: AutoCompleteTextView.Validator?): Boolean {
        if (item == null) return false

        val text = "The quick brown fox jumps over the lazy dog"
        return validator.isValid(text) == item.isValid(text) && validator.fixText(text) == item.fixText(text)
    }

    override fun describeTo(description: Description?) {
        description?.appendText("view has validator that is compatible")
    }

}
