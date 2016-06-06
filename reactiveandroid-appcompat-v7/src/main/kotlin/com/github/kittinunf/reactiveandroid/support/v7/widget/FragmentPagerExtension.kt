package com.github.kittinunf.reactiveandroid.support.v7.widget

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import android.support.v4.view.ViewPager
import com.github.kittinunf.reactiveandroid.scheduler.AndroidThreadScheduler
import rx.Observable
import rx.Subscription

abstract class FragmentPagerProxyAdapter<ARG>(fragmentManager: FragmentManager) : FragmentPagerAdapter(fragmentManager) {

    internal var items: List<ARG> = listOf()

    abstract var pageTitle: ((Int, ARG) -> String)

    abstract var item: ((Int, ARG) -> Fragment)

    override fun getItem(position: Int): Fragment? = item.invoke(position, items[position])

    override fun getPageTitle(position: Int): CharSequence? = pageTitle.invoke(position, items[position])

    override fun getCount(): Int = items.size

    override fun getItemId(position: Int): Long = position.toLong()

}

fun <ARG, L : List<ARG>> ViewPager.rx_fragmentsWith(observable: Observable<L>, fragmentManager: FragmentManager,
                                                    getItem: (Int, ARG) -> Fragment,
                                                    getPageTitle: ((Int, ARG) -> String)): Subscription {
    val proxyAdapter = object : FragmentPagerProxyAdapter<ARG>(fragmentManager) {

        override var item: (Int, ARG) -> Fragment = getItem
        override var pageTitle: ((Int, ARG) -> String) = getPageTitle

    }
    return rx_fragmentsWith(observable, proxyAdapter)
}

fun <ARG, ADT : FragmentPagerProxyAdapter<ARG>, L : List<ARG>> ViewPager.rx_fragmentsWith(observable: Observable<L>, fragmentPagerProxyAdapter: ADT): Subscription {
    adapter = fragmentPagerProxyAdapter
    return observable.observeOn(AndroidThreadScheduler.mainThreadScheduler).subscribe {
        fragmentPagerProxyAdapter.items = it
        fragmentPagerProxyAdapter.notifyDataSetChanged()
    }
}