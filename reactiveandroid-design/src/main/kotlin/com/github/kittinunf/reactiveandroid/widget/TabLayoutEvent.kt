package com.github.kittinunf.reactiveandroid.widget

import android.support.design.widget.TabLayout
import com.github.kittinunf.reactiveandroid.ExtensionFieldDelegate
import com.github.kittinunf.reactiveandroid.subscription.AndroidMainThreadSubscription
import rx.Observable

//================================================================================
// Events
//================================================================================

fun TabLayout.rx_tabSelected(): Observable<TabLayout.Tab> {
    return Observable.create { subscriber ->
        _tabSelected.onTabSelected {
            subscriber.onNext(it)
        }

        subscriber.add(AndroidMainThreadSubscription {
            setOnTabSelectedListener(null)
        })
    }
}

fun TabLayout.rx_tabUnSelected(): Observable<TabLayout.Tab> {
    return Observable.create { subscriber ->
        _tabSelected.onTabUnselected {
            subscriber.onNext(it)
        }

        subscriber.add(AndroidMainThreadSubscription {
            setOnTabSelectedListener(null)
        })
    }
}

fun TabLayout.rx_tabReSelected(): Observable<TabLayout.Tab> {
    return Observable.create { subscriber ->
        _tabSelected.onTabReselected {
            subscriber.onNext(it)
        }

        subscriber.add(AndroidMainThreadSubscription {
            setOnTabSelectedListener(null)
        })
    }
}

private val TabLayout._tabSelected: _TabLayout_OnTabSelectedListener
        by ExtensionFieldDelegate({ _TabLayout_OnTabSelectedListener() }, { setOnTabSelectedListener(it) })

class _TabLayout_OnTabSelectedListener : TabLayout.OnTabSelectedListener {

    private var onTabReselected: ((TabLayout.Tab?) -> Unit)? = null

    private var onTabUnselected: ((TabLayout.Tab?) -> Unit)? = null

    private var onTabSelected: ((TabLayout.Tab?) -> Unit)? = null

    fun onTabReselected(listener: (TabLayout.Tab?) -> Unit) {
        onTabReselected = listener
    }

    override fun onTabReselected(tab: TabLayout.Tab?) {
        onTabReselected?.invoke(tab)
    }

    fun onTabUnselected(listener: (TabLayout.Tab?) -> Unit) {
        onTabUnselected = listener
    }

    override fun onTabUnselected(tab: TabLayout.Tab?) {
        onTabUnselected?.invoke(tab)
    }

    fun onTabSelected(listener: (TabLayout.Tab?) -> Unit) {
        onTabSelected = listener
    }

    override fun onTabSelected(tab: TabLayout.Tab?) {
        onTabSelected?.invoke(tab)
    }

}
