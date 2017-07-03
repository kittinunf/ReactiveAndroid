package com.github.kittinunf.reactiveandroid.support.design.widget

import android.support.design.widget.TabLayout
import com.github.kittinunf.reactiveandroid.ExtensionFieldDelegate
import com.github.kittinunf.reactiveandroid.subscription.AndroidMainThreadSubscription
import io.reactivex.Observable

//================================================================================
// Events
//================================================================================

fun TabLayout.rx_tabSelected(): Observable<TabLayout.Tab> {
    return Observable.create { subscriber ->
        _tabSelected.onTabSelected {
            if (it != null) subscriber.onNext(it)
        }

        subscriber.setDisposable(AndroidMainThreadSubscription {
            setOnTabSelectedListener(null)
        })
    }
}

fun TabLayout.rx_tabUnselected(): Observable<TabLayout.Tab> {
    return Observable.create { subscriber ->
        _tabSelected.onTabUnselected {
            if (it != null) subscriber.onNext(it)
        }

        subscriber.setDisposable(AndroidMainThreadSubscription {
            setOnTabSelectedListener(null)
        })
    }
}

fun TabLayout.rx_tabReselected(): Observable<TabLayout.Tab> {
    return Observable.create { subscriber ->
        _tabSelected.onTabReselected {
            if (it != null) subscriber.onNext(it)
        }

        subscriber.setDisposable(AndroidMainThreadSubscription {
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
