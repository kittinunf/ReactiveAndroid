package com.github.kittinunf.reactiveandroid

import com.github.kittinunf.reactiveandroid.rx.add
import com.github.kittinunf.reactiveandroid.rx.addTo
import org.junit.Assert.assertThat
import org.junit.Test
import rx.Observable
import rx.subscriptions.CompositeSubscription
import org.hamcrest.CoreMatchers.`is` as isEqualTo

class SubscriptionExtensionTest {

    val compositeSubscription = CompositeSubscription()

    @Test
    fun addTo() {
        var called = false
        val o = Observable.create<String> { subscriber ->
            subscriber.add { called = true }
        }
        o.subscribe().addTo(compositeSubscription)

        compositeSubscription.unsubscribe()

        assertThat(called, isEqualTo(true))
    }
}