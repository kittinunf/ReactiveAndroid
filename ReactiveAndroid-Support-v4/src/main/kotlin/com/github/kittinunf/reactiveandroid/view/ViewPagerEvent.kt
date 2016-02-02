package com.github.kittinunf.reactiveandroid.view

import android.support.v4.view.ViewPager
import com.github.kittinunf.reactiveandroid.subscription.AndroidMainThreadSubscription
import rx.Observable

//================================================================================
// Events
//================================================================================

fun ViewPager.rx_pageChange(): Observable<Int> {
    return Observable.create { subscriber ->
        val listener = object : ViewPager.OnPageChangeListener {
            override fun onPageScrollStateChanged(state: Int) {
            }

            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
            }

            override fun onPageSelected(position: Int) {
                subscriber.onNext(position)
            }
        }
        addOnPageChangeListener(listener)
        
        subscriber.add(AndroidMainThreadSubscription {
            removeOnPageChangeListener(listener)
        })
    }
}

 
