package com.github.kittinunf.reactiveandroid.scheduler

import rx.android.schedulers.AndroidSchedulers

object AndroidThreadScheduler {

    val mainThreadScheduler by lazy { AndroidSchedulers.mainThread() }

}