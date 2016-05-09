package com.github.kittinunf.reactiveandroid.sample.view

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.github.kittinunf.reactiveandroid.sample.R
import com.github.kittinunf.reactiveandroid.support.v7.widget.rx_fragmentsWith
import com.judrummer.androidbestrhythm.viewpager.section.PlaceholderFragment
import kotlinx.android.synthetic.main.activity_fragment_pager.*
import rx.Observable

class FragmentPagerActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_fragment_pager)
        setUpUI()
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
        )
        tlPager.setupWithViewPager(viewPager)
    }

}
