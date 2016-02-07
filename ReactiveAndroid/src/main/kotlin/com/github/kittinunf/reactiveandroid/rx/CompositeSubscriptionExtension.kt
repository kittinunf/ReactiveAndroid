package com.github.kittinunf.reactiveandroid.rx

import rx.Subscription
import rx.subscriptions.CompositeSubscription

infix operator fun CompositeSubscription.plusAssign(subscription: Subscription?) = add(subscription)
