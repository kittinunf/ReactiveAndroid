package com.github.kittinunf.reactiveandroid.sample.view

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.github.kittinunf.reactiveandroid.helper.Observables
import com.github.kittinunf.reactiveandroid.reactive.addTo
import com.github.kittinunf.reactiveandroid.reactive.view.rx
import com.github.kittinunf.reactiveandroid.reactive.widget.text
import com.github.kittinunf.reactiveandroid.reactive.widget.textChanged
import com.github.kittinunf.reactiveandroid.sample.R
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_adding_numbers.number1
import kotlinx.android.synthetic.main.activity_adding_numbers.number2
import kotlinx.android.synthetic.main.activity_adding_numbers.number3
import kotlinx.android.synthetic.main.activity_adding_numbers.result

class AddingNumbersActivity : AppCompatActivity() {

    val disposables = CompositeDisposable()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_adding_numbers)

        Observables.combineLatest(
                number1.rx.textChanged().map { it.s.toString().toIntOrNull() ?: 0 },
                number2.rx.textChanged().map { it.s.toString().toIntOrNull() ?: 0 },
                number3.rx.textChanged().map { it.s.toString().toIntOrNull() ?: 0 }) { n1, n2, n3 -> (n1 + n2 + n3).toString() }
                .observeOn(Schedulers.io())
                .subscribeOn(AndroidSchedulers.mainThread())
                .subscribe(result.rx.text)
                .addTo(disposables)
    }

    override fun onDestroy() {
        super.onDestroy()
        disposables.dispose()
    }
}
