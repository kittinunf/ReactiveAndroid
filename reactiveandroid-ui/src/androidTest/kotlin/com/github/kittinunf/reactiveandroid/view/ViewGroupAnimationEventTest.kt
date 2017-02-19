package com.github.kittinunf.reactiveandroid.view

import android.support.test.InstrumentationRegistry
import android.support.test.rule.ActivityTestRule
import android.support.test.runner.AndroidJUnit4
import android.view.animation.Animation
import android.view.animation.LayoutAnimationController
import android.view.animation.RotateAnimation
import android.view.animation.TranslateAnimation
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import rx.observers.TestSubscriber

@RunWith(AndroidJUnit4::class)
class ViewGroupAnimationEventTest {

    @Rule
    @JvmField
    val activityRule = ActivityTestRule(ViewAttachTestActivity::class.java)

    val instrumentation = InstrumentationRegistry.getInstrumentation()

    @Test
    fun testLayoutAnimation() {
        val parent = activityRule.activity.parent
        val child = activityRule.activity.child

        instrumentation.runOnMainSync {
            parent.addView(child)
        }

        val translateAnimation = TranslateAnimation(0.0f, 10.0f, 0.0f, 20.0f)
        translateAnimation.duration = 100
        parent.layoutAnimation = LayoutAnimationController(translateAnimation)

        val t1 = TestSubscriber<Animation>()
        val t2 = TestSubscriber<Animation>()
        parent.rx_animationStart().subscribe(t1)
        parent.rx_animationEnd().subscribe(t2)

        t1.assertNoValues()
        t1.assertNoErrors()
        t2.assertNoValues()
        t2.assertNoErrors()

        instrumentation.runOnMainSync {
            parent.startLayoutAnimation()
        }

        t1.assertValueCount(1)

        val rotateAnimation = RotateAnimation(0.0f, 360.0f)
        rotateAnimation.repeatCount = Animation.INFINITE
        parent.layoutAnimation = LayoutAnimationController(rotateAnimation)
        val t3 = TestSubscriber<Animation>()
        parent.rx_animationRepeat().subscribe(t3)

        t3.assertNoValues()
        t3.assertNoErrors()

        instrumentation.runOnMainSync {
            parent.startLayoutAnimation()
        }

        t3.assertValueCount(1)
    }

}