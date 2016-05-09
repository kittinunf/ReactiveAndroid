package com.github.kittinunf.reactiveandroid.support.v7.widget

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import android.support.v4.view.ViewPager
import rx.Observable
import rx.Subscription

/**
 * Created by tipatai on 5/3/16 AD.
 */

abstract class FragmentPagerProxyAdapter<ARG>(fragmentManager: FragmentManager) : FragmentPagerAdapter (fragmentManager) {

    internal var items: List<ARG> = listOf()

    open var pageTitle: ((Int, ARG) -> String)? = null

    abstract var item: ((Int, ARG) -> Fragment)

    override fun getItem(position: Int): Fragment? = item.invoke(position, items[position])

    override fun getPageTitle(position: Int): CharSequence? =
            pageTitle?.let {
                it.invoke(position, items[position])
            } ?: {
                super.getPageTitle(position)
            }()

    override fun getCount(): Int = items.size

    override fun getItemId(position: Int): Long = position.toLong()

}

fun <ARG, L : List<ARG>> ViewPager.rx_itemsWith(observable: Observable<L>, fragmentManager: FragmentManager,
                                                getItem: (Int, ARG) -> Fragment,
                                                getPageTitle: ((Int, ARG) -> String)? = null,
                                                getPageWidth: ((Int) -> Float)? = null): Subscription {
    val proxyAdapter = object : FragmentPagerProxyAdapter<ARG>(fragmentManager) {

        override var item: (Int, ARG) -> Fragment = getItem
        override var pageTitle: ((Int, ARG) -> String)? = getPageTitle

    }
    return rx_itemsWith(observable, proxyAdapter)
}

fun <ARG, ADT : FragmentPagerProxyAdapter<ARG>, L : List<ARG>> ViewPager.rx_itemsWith(observable: Observable<L>, fragmentPagerProxyAdapter: ADT): Subscription {
    adapter = fragmentPagerProxyAdapter
    return observable.subscribe {
        fragmentPagerProxyAdapter.items = it
        fragmentPagerProxyAdapter.notifyDataSetChanged()
    }
}