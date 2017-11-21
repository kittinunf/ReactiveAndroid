package com.github.kittinunf.reactiveandroid.sample.view

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.Menu
import android.view.View
import android.widget.TextView
import com.github.kittinunf.reactiveandroid.MutableProperty
import com.github.kittinunf.reactiveandroid.reactive.addTo
import com.github.kittinunf.reactiveandroid.sample.R
import com.github.kittinunf.reactiveandroid.support.v7.widget.SECTION_HEADER_VIEW_TYPE
import com.github.kittinunf.reactiveandroid.support.v7.widget.mapToSection
import com.github.kittinunf.reactiveandroid.support.v7.widget.rx_itemsWith
import com.github.kittinunf.reactiveandroid.view.rx_menuItemClick
import io.reactivex.disposables.CompositeDisposable
import kotlinx.android.synthetic.main.activity_section_recycler_view.recyclerView
import kotlinx.android.synthetic.main.activity_section_recycler_view.titleTextView
import kotlinx.android.synthetic.main.activity_section_recycler_view.toolbar
import kotlinx.android.synthetic.main.recycler_header_item.view.recyclerHeaderTextView
import kotlinx.android.synthetic.main.recycler_item.view.recyclerItemTextView2
import kotlinx.android.synthetic.main.recycler_item.view.textView1
import java.util.*

class SectionRecyclerViewActivity : AppCompatActivity() {

    val compositeSubscription = CompositeDisposable()

    //back storage
    val _sections = mutableListOf<Country>()

    val sections = MutableProperty<List<Country>>()

    lateinit var countriesAndCapitals: Array<Country>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_section_recycler_view)
        setSupportActionBar(toolbar)

        titleTextView.text = javaClass.simpleName

        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager = LinearLayoutManager(this)

        val rawCountries = resources.getStringArray(R.array.countries)
        val rawCapitals = resources.getStringArray(R.array.capitals)

        countriesAndCapitals = rawCountries.mapIndexed { index, country ->
            val capital = rawCapitals[index]
            Country(country, capital)
        }.toTypedArray()

        recyclerView.rx_itemsWith(sections.observable.mapToSection { (it.name.first()) }, { parent, viewType ->
            val resId = if (viewType == SECTION_HEADER_VIEW_TYPE) R.layout.recycler_header_item else R.layout.recycler_item
            val view = LayoutInflater.from(parent?.context).inflate(resId, parent, false)
            ViewHolder(view)
        }, { holder, position, section ->
            holder.headerTextView.text = section.name
        }, { holder, position, item ->
            holder.textView1.text = item.name
            holder.textView2.text = item.capital
        }).addTo(compositeSubscription)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menu?.let {
            menuInflater.inflate(R.menu.menus, menu)
            menu.findItem(R.id.menu_add).rx_menuItemClick(true).subscribe {
                val r = Random().nextInt(countriesAndCapitals.size)
                _sections.add(countriesAndCapitals[r])
                sections.value = _sections.toList()
            }.addTo(compositeSubscription)
        }
        return true
    }

    override fun onDestroy() {
        super.onDestroy()
        compositeSubscription.dispose()
    }

    class Country(val name: String, val capital: String)

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val headerTextView: TextView by lazy(LazyThreadSafetyMode.NONE) { view.recyclerHeaderTextView }

        val textView1: TextView by lazy(LazyThreadSafetyMode.NONE) { view.textView1}
        val textView2: TextView by lazy(LazyThreadSafetyMode.NONE) { view.recyclerItemTextView2 }
    }

}
