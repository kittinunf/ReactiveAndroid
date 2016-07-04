package com.github.kittinunf.reactiveandroid.sample.view

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import com.github.kittinunf.reactiveandroid.rx.addTo
import com.github.kittinunf.reactiveandroid.sample.R
import com.github.kittinunf.reactiveandroid.sample.fragment.PlaceholderFragment
import com.github.kittinunf.reactiveandroid.support.v7.widget.rx_fragmentsWith
import com.github.kittinunf.reactiveandroid.view.rx_attachedToWindow
import com.github.kittinunf.reactiveandroid.view.rx_detachedFromWindow
import com.github.kittinunf.reactiveandroid.widget.rx_tabSelected
import com.github.kittinunf.reactiveandroid.widget.rx_tabUnSelected
import kotlinx.android.synthetic.main.activity_fragment_pager.*
import rx.Observable
import rx.subscriptions.CompositeSubscription

class FragmentPagerActivity : AppCompatActivity() {

    val subscriptions = CompositeSubscription()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_fragment_pager)
        setUpUI()
    }

    override fun onDestroy() {
        super.onDestroy()
    }

    fun setUpUI() {
        setSupportActionBar(toolbar)
        viewPager.rx_fragmentsWith(Observable.just(listOf("section1", "section2", "section3")), supportFragmentManager,
                { position, item ->
                    PlaceholderFragment.newInstance(item)
                },
                { position, item ->
                    item
                }
        ).addTo(subscriptions)

        viewPager.rx_attachedToWindow().subscribe {
            Log.e(javaClass.simpleName, "viewPager is attached")
        }.addTo(subscriptions)

        viewPager.rx_detachedFromWindow().subscribe {
            Log.e(javaClass.simpleName, "viewPager is detached")
        }.addTo(subscriptions)

        tlPager.setupWithViewPager(viewPager)

        tlPager.rx_tabSelected().subscribe {
            Log.e(javaClass.simpleName, "selected: ${it.position}")
        }.addTo(subscriptions)

        tlPager.rx_tabUnSelected().subscribe {
            Log.e(javaClass.simpleName, "unselected: ${it.position}")
        }.addTo(subscriptions)
    }

}
