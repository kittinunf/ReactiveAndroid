package com.github.kittinunf.reactiveandroid.sample.fragment

import android.os.Bundle
import android.support.v4.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.github.kittinunf.reactiveandroid.reactive.addTo
import com.github.kittinunf.reactiveandroid.sample.R
import com.github.kittinunf.reactiveandroid.support.design.widget.rx_tabSelected
import com.github.kittinunf.reactiveandroid.support.design.widget.rx_tabUnselected
import com.github.kittinunf.reactiveandroid.support.v4.widget.rx_fragmentsStateWith
import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable
import kotlinx.android.synthetic.main.fragment_view_pager.tlPager
import kotlinx.android.synthetic.main.fragment_view_pager.viewPager

class ViewPagerFragment : Fragment() {

    val subscriptions = CompositeDisposable()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_view_pager, container, false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewPager.rx_fragmentsStateWith(Observable.just(listOf("section1", "section2", "section3")), fragmentManager,
                { position, item ->
                    PlaceholderFragment.newInstance(item)
                },
                { position, item ->
                    item
                }
        ).addTo(subscriptions)

//        viewPager.rx_attachedToWindow().subscribe {
//            Log.e(javaClass.simpleName, "viewPager is attached")
//        }.addTo(subscriptions)
//
//        viewPager.rx_detachedFromWindow().subscribe {
//            Log.e(javaClass.simpleName, "viewPager is detached")
//        }.addTo(subscriptions)

        tlPager.setupWithViewPager(viewPager)

        tlPager.rx_tabSelected().subscribe {
            Log.e(javaClass.simpleName, "selected ${it.position}")
        }.addTo(subscriptions)

        tlPager.rx_tabUnselected().subscribe {
            Log.e(javaClass.simpleName, "unselected ${it.position}")
        }.addTo(subscriptions)
    }

    companion object {

        fun newInstance(): ViewPagerFragment {
            return ViewPagerFragment()
        }
    }
}
