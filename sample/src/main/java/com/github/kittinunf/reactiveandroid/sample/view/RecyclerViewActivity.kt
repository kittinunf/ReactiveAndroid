package com.github.kittinunf.reactiveandroid.sample.view

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuItem
import android.view.View
import com.github.kittinunf.reactiveandroid.reactive.addTo
import com.github.kittinunf.reactiveandroid.reactive.view.click
import com.github.kittinunf.reactiveandroid.reactive.view.longClick
import com.github.kittinunf.reactiveandroid.reactive.view.rx
import com.github.kittinunf.reactiveandroid.sample.R
import com.github.kittinunf.reactiveandroid.support.v7.reactive.widget.bind
import com.github.kittinunf.reactiveandroid.support.v7.reactive.widget.rx
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.subjects.BehaviorSubject
import kotlinx.android.synthetic.main.activity_recycler_view.recyclerView
import kotlinx.android.synthetic.main.activity_recycler_view.toolbar
import kotlinx.android.synthetic.main.recycler_item.view.textView1
import java.util.*

class RecyclerViewActivity : AppCompatActivity() {

    private lateinit var countries: List<String>

    private val itemSubject = BehaviorSubject.create<List<String>>()

    private val disposables = CompositeDisposable()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recycler_view)
        setSupportActionBar(toolbar)

        countries = resources.getStringArray(R.array.countries).toList()
        itemSubject.onNext(countries)

        recyclerView.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(this@RecyclerViewActivity)
        }

        recyclerView.rx.bind(itemSubject.hide(),
                onCreateViewHolder = { parent, _ ->
                    val view = LayoutInflater.from(parent?.context).inflate(R.layout.recycler_item, parent, false)
                    CountryViewHolder(view).apply {
                        itemView.rx.click()
                                .subscribe {
                                    val mutableList = itemSubject.value.toMutableList()
                                    mutableList[layoutPosition] = countries.random()
                                    itemSubject.onNext(mutableList)
                                }

                        itemView.rx.longClick()
                                .subscribe {
                                    val mutableList = itemSubject.value.toMutableList()
                                    mutableList.removeAt(layoutPosition)
                                    itemSubject.onNext(mutableList)
                                }
                    }
                },
                onBindViewHolder = { viewHolder, _, item ->
                    viewHolder.itemView.textView1.text = item
                })
                .addTo(disposables)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menus, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
           R.id.menu_add -> {
               val mutableList = itemSubject.value.toMutableList()
               mutableList.add(countries.random())
               itemSubject.onNext(mutableList)

               recyclerView.smoothScrollToPosition(mutableList.lastIndex)
           }
        }
        return super.onOptionsItemSelected(item)
    }

    class CountryViewHolder(view: View) : RecyclerView.ViewHolder(view)

    fun Int.random() = Random().nextInt(this + 1)

    fun <T> List<T>.random() = this[this.size.random()]
}