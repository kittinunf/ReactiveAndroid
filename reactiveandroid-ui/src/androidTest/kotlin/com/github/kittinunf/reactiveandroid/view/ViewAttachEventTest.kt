package com.github.kittinunf.reactiveandroid.view

import android.support.test.InstrumentationRegistry
import android.support.test.rule.ActivityTestRule
import android.support.test.runner.AndroidJUnit4
import android.view.View
import io.reactivex.observers.TestObserver
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class ViewAttachEventTest {

    @Rule
    @JvmField
    val activityRule = ActivityTestRule(ViewTestActivity::class.java)

    val instrumentation = InstrumentationRegistry.getInstrumentation()

    @Test
    fun testAttachStateChange() {
        val parent = activityRule.activity.parent
        val child = activityRule.activity.child

        val t1 = TestObserver<View>()
        val t2 = TestObserver<View>()

        val s1 = child.rx_attachedToWindow().subscribeWith(t1)
        val s2 = child.rx_detachedFromWindow().subscribeWith(t2)

        t1.assertNoValues()
        t1.assertNoErrors()
        t2.assertNoValues()
        t2.assertNoValues()

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
            s1.dispose()
            s2.dispose()
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
