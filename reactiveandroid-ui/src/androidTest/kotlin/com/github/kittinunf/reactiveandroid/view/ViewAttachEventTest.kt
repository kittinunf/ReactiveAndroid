package com.github.kittinunf.reactiveandroid.view

import android.support.test.InstrumentationRegistry
import android.support.test.rule.ActivityTestRule
import android.support.test.runner.AndroidJUnit4
import android.view.View
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import rx.observers.TestSubscriber

@RunWith(AndroidJUnit4::class)
class ViewAttachEventTest {

    @Rule @JvmField
    val activityRule = ActivityTestRule(ViewAttachActivity::class.java)

    val instrumentation = InstrumentationRegistry.getInstrumentation()

    @Test
    fun testAttachStateChange() {
        val activity = activityRule.activity

        val parent = activity.parent
        val child = activity.child

        val t1 = TestSubscriber<View>()
        val t2 = TestSubscriber<View>()

        val s1 = child.rx_attachedToWindow().subscribe(t1)
        val s2 = child.rx_detachedFromWindow().subscribe(t2)

        t1.assertNoValues()
        t1.assertNoErrors()

        instrumentation.runOnMainSync {
            parent.addView(child)
        }

        t1.assertValueCount(1)
        t2.assertNoValues()

        instrumentation.runOnMainSync {
            parent.removeView(child)
        }

        t1.assertValueCount(1)
        t2.assertValueCount(1)

        instrumentation.runOnMainSync {
            s1.unsubscribe()
            s2.unsubscribe()
        }

        instrumentation.runOnMainSync {
            parent.addView(child)
        }

        instrumentation.runOnMainSync {
            parent.removeView(child)
        }

        t1.assertValueCount(1)
        t2.assertValueCount(1)
    }

}
