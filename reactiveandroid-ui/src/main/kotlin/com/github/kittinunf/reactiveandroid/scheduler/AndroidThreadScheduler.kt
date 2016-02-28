package com.github.kittinunf.reactiveandroid.scheduler

import android.os.Handler
import android.os.Looper
import rx.Scheduler
import rx.Subscription
import rx.functions.Action0
import rx.internal.schedulers.ScheduledAction
import rx.subscriptions.CompositeSubscription
import rx.subscriptions.Subscriptions
import java.util.concurrent.TimeUnit

object AndroidThreadScheduler {

    val mainThreadScheduler by lazy { MainThreadScheduler() }

    class MainThreadScheduler : Scheduler() {

        private val mainThreadWorker by lazy { ThreadWorker(Handler(Looper.getMainLooper())) }

        override fun createWorker(): Worker? {
            return mainThreadWorker
        }

    }

    private class ThreadWorker(val handler: Handler) : Scheduler.Worker() {

        val subscriptions = CompositeSubscription()

        override fun isUnsubscribed() = subscriptions.isUnsubscribed

        override fun unsubscribe() = subscriptions.unsubscribe()

        override fun schedule(action: Action0?): Subscription? = schedule(action, 0, TimeUnit.MILLISECONDS)

        override fun schedule(action: Action0?, delayTime: Long, unit: TimeUnit): Subscription? {
            if (subscriptions.isUnsubscribed) {
                subscriptions.unsubscribe()
            }

            val scheduledAction = ScheduledAction(action).apply {
                addParent(subscriptions)
                add(Subscriptions.create {
                    handler.removeCallbacks(this)
                })
            }

            subscriptions.add(scheduledAction)

            handler.postDelayed(scheduledAction, unit.toMillis(delayTime))
            return scheduledAction
        }

    }

}