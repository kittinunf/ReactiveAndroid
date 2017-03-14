package com.github.kittinunf.reactiveandroid.sample.view

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import com.github.kittinunf.reactiveandroid.rx.addTo
import com.github.kittinunf.reactiveandroid.sample.R
import com.github.kittinunf.reactiveandroid.sample.fragment.ViewPagerFragment
import com.github.kittinunf.reactiveandroid.support.design.widget.rx_itemBackgroundResource
import com.github.kittinunf.reactiveandroid.support.design.widget.rx_itemIconTintList
import com.github.kittinunf.reactiveandroid.support.design.widget.rx_itemTextColor
import com.github.kittinunf.reactiveandroid.support.design.widget.rx_navigationItemSelected
import kotlinx.android.synthetic.main.activity_fragment_pager.*
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

        replaceFragment(ViewPagerFragment.newInstance())

        bottomNavigationView.rx_navigationItemSelected(true).subscribe {
            when (it.itemId) {
                R.id.action_favorites -> replaceFragment(ViewPagerFragment.newInstance())
                R.id.action_schedules -> replaceFragment(ViewPagerFragment.newInstance())
                R.id.action_music -> replaceFragment(ViewPagerFragment.newInstance())
            }
        }.addTo(subscriptions)

        bottomNavigationView.rx_itemBackgroundResource.value = R.color.colorPrimary
        bottomNavigationView.rx_itemIconTintList.value = resources.getColorStateList(R.drawable.tabs_color_state)
        bottomNavigationView.rx_itemTextColor.value = resources.getColorStateList(R.drawable.tabs_color_state)
    }

    fun replaceFragment(fragment: Fragment) {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.container, fragment).commit()
    }
}
