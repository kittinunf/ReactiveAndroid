package com.github.kittinunf.reactiveandroid.scheduler

import rx.android.schedulers.AndroidSchedulers

object AndroidThreadScheduler {

    val main by lazy { AndroidSchedulers.mainThread() }

}