package com.github.kittinunf.reactiveandroid.sample.view

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.github.kittinunf.reactiveandroid.Property
import com.github.kittinunf.reactiveandroid.rx.addTo
import com.github.kittinunf.reactiveandroid.rx.bindTo
import com.github.kittinunf.reactiveandroid.sample.R
import com.github.kittinunf.reactiveandroid.scheduler.AndroidThreadScheduler
import com.github.kittinunf.reactiveandroid.view.rx_click
import com.github.kittinunf.reactiveandroid.widget.AdapterViewProxyAdapter
import com.github.kittinunf.reactiveandroid.widget.rx_itemsWith
import com.github.kittinunf.reactiveandroid.widget.rx_text
import kotlinx.android.synthetic.main.activity_landing.*
import kotlinx.android.synthetic.main.spinner_item.view.*
import kotlinx.android.synthetic.main.spinner_item_dropdown.view.*
import rx.Observable
import rx.schedulers.Schedulers
import rx.subscriptions.CompositeSubscription
import java.util.concurrent.TimeUnit
import kotlin.reflect.KClass

class LandingActivity : AppCompatActivity() {

    val subscriptions = CompositeSubscription()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_landing)
        setUpToolbar()
        setUpButtons()
        setUpTextView()
    }

    override fun onDestroy() {
        super.onDestroy()
        subscriptions.unsubscribe()
    }

    private fun setUpToolbar() {
        setSupportActionBar(toolbar)

        val property = Property(listOf("Item 1", "Item 2", "Item 3"))
        toolbarSpinner.rx_itemsWith(property.observable, ItemAdapter()).addTo(subscriptions)
        title = ""
    }

    private fun setUpButtons() {
        toSignInPageButton.rx_click().map { SignInActivity::class }.bindTo(this, LandingActivity::start).addTo(subscriptions)
        toSignUpPageButton.rx_click().map { SignUpActivity::class }.bindTo(this, LandingActivity::start).addTo(subscriptions)
        toListPageButton.rx_click().map { RecyclerViewActivity::class }.bindTo(this, LandingActivity::start).addTo(subscriptions)
        toSectionListPageButton.rx_click().map { SectionRecyclerViewActivity::class }.bindTo(this, LandingActivity::start).addTo(subscriptions)
        toFragmentPagerButton.rx_click().map { FragmentPagerActivity::class }.bindTo(this, LandingActivity::start).addTo(subscriptions)
        toNestedListButton.rx_click().map { NestedRecyclerViewActivity::class }.bindTo(this, LandingActivity::start).addTo(subscriptions)
    }

    private fun setUpTextView() {
        Observable.interval(5, TimeUnit.SECONDS, Schedulers.computation())
                .map { it.toString() }
                .observeOn(AndroidThreadScheduler.mainThreadScheduler)
                .bindTo(resultTextView.rx_text)
                .addTo(subscriptions)
    }

    fun start(clazz: KClass<*>) {
        startActivity(Intent(this, clazz.java))
    }

    inner class ItemAdapter : AdapterViewProxyAdapter<String>() {
        override fun getItemId(position: Int) = position.toLong()

        override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View? {
            val view = convertView ?: LayoutInflater.from(this@LandingActivity).inflate(R.layout.spinner_item, parent, false)
            view.spinnerTextView.text = this[position]
            return view
        }

        override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup?): View? {
            val view = convertView ?: LayoutInflater.from(this@LandingActivity).inflate(R.layout.spinner_item_dropdown, parent, false)
            view.spinnerTextViewDropdown.text = this[position]
            return view
        }
    }

}