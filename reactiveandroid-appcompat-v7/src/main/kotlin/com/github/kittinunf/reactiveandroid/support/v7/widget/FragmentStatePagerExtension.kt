package com.github.kittinunf.reactiveandroid.support.v7.widget

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentStatePagerAdapter
import android.support.v4.view.ViewPager
import rx.Observable
import rx.Subscription

abstract class FragmentStatePagerProxyAdapter<ARG>(fragmentManager: FragmentManager) : FragmentStatePagerAdapter(fragmentManager) {

    internal var items: List<ARG> = listOf()

    abstract var pageTitle: ((Int, ARG) -> String)

    abstract var item: ((Int, ARG) -> Fragment)

    override fun getItem(position: Int): Fragment? = item.invoke(position, items[position])

    override fun getPageTitle(position: Int): CharSequence? = pageTitle.invoke(position, items[position])

    override fun getCount(): Int = items.size

}

fun <ARG, L : List<ARG>> ViewPager.rx_fragmentsStateWith(observable: Observable<L>, fragmentManager: FragmentManager,
                                                         getItem: (Int, ARG) -> Fragment,
                                                         getPageTitle: ((Int, ARG) -> String)): Subscription {
    val proxyAdapter = object : FragmentStatePagerProxyAdapter<ARG>(fragmentManager) {

        override var item: (Int, ARG) -> Fragment = getItem
        override var pageTitle: ((Int, ARG) -> String) = getPageTitle

    }
    return rx_fragmentsStateWith(observable, proxyAdapter)
}

fun <ARG, ADT : FragmentStatePagerProxyAdapter<ARG>, L : List<ARG>> ViewPager.rx_fragmentsStateWith(observable: Observable<L>, fragmentPagerProxyAdapter: ADT): Subscription {
    adapter = fragmentPagerProxyAdapter
    return observable.subscribe {
        fragmentPagerProxyAdapter.items = it
        fragmentPagerProxyAdapter.notifyDataSetChanged()
    }
}