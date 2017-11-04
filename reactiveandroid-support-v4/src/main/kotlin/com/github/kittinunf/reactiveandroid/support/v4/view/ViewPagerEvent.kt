package com.github.kittinunf.reactiveandroid.support.v4.view

import android.support.v4.view.ViewPager
import com.github.kittinunf.reactiveandroid.ExtensionFieldDelegate
import com.github.kittinunf.reactiveandroid.subscription.AndroidMainThreadSubscription
import io.reactivex.Observable

//================================================================================
// Events
//================================================================================

fun ViewPager.rx_pageScrollStateChanged(): Observable<Int> {
    return Observable.create { subscriber ->
        _pageChange.onPageScrollStateChanged {
            subscriber.onNext(it)
        }

        subscriber.setDisposable(AndroidMainThreadSubscription {
            removeOnPageChangeListener(_pageChange)
        })
    }
}

data class PageScrolledListener(val position: Int, val positionOffset: Float, val positionOffsetPixels: Int)

fun ViewPager.rx_pageScrolled(): Observable<PageScrolledListener> {
    return Observable.create { subscriber ->
        _pageChange.onPageScrolled { position, positionOffset, positionOffsetPixels ->
            subscriber.onNext(PageScrolledListener(position, positionOffset, positionOffsetPixels))
        }

        subscriber.setDisposable(AndroidMainThreadSubscription {
            removeOnPageChangeListener(_pageChange)
        })
    }
}

fun ViewPager.rx_pageSelected(): Observable<Int> {
    return Observable.create { subscriber ->
        _pageChange.onPageSelected {
            subscriber.onNext(it)
        }

        subscriber.setDisposable(AndroidMainThreadSubscription {
            removeOnPageChangeListener(_pageChange)
        })
    }
}

private val ViewPager._pageChange: _ViewPager_OnPageChangeListener
        by ExtensionFieldDelegate({ _ViewPager_OnPageChangeListener() }, { addOnPageChangeListener(it) })

private class _ViewPager_OnPageChangeListener : ViewPager.OnPageChangeListener {

    private var onPageScrollStateChanged: ((Int) -> Unit)? = null

    private var onPageScrolled: ((Int, Float, Int) -> Unit)? = null

    private var onPageSelected: ((Int) -> Unit)? = null

    fun onPageScrollStateChanged(listener: (Int) -> Unit) {
        onPageScrollStateChanged = listener
    }

    override fun onPageScrollStateChanged(state: Int) {
        onPageScrollStateChanged?.invoke(state)
    }

    fun onPageScrolled(listener: (Int, Float, Int) -> Unit) {
        onPageScrolled = listener
    }

    override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
        onPageScrolled?.invoke(position, positionOffset, positionOffsetPixels)
    }

    fun onPageSelected(listener: (Int) -> Unit) {
        onPageSelected = listener
    }

    override fun onPageSelected(position: Int) {
        onPageSelected?.invoke(position)
    }

}
