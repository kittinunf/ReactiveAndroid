package com.github.kittinunf.reactiveandroid.sample.view

import android.os.Bundle
import android.support.design.widget.FloatingActionButton
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.view.View

import com.github.kittinunf.reactiveandroid.sample.R
import com.github.kittinunf.reactiveandroid.support.v7.widget.rx_itemsWith
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
        viewPager.rx_itemsWith(Observable.just(listOf("section1", "section2", "section3")), supportFragmentManager,
                { position, item ->
                    PlaceholderFragment.newInstance(item)
                },
                { position, item ->
                    item
                },
                { position ->
                    0.9f
                }
        )
        tlPager.apply {
            setupWithViewPager(viewPager)
        }
    }

}