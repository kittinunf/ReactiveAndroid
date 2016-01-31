package com.github.kittinunf.reactiveandroid.rx

import rx.Subscription
import rx.subscriptions.CompositeSubscription

/**
 * Created by Kittinun Vantasin on 1/30/16.
 */

infix operator fun CompositeSubscription.plusAssign(subscription: Subscription?) = add(subscription)
