package com.github.kittinunf.reactiveandroid.sample.view

import android.support.v7.app.AppCompatActivity
import android.os.Bundle

import com.github.kittinunf.reactiveandroid.sample.R
import io.reactivex.disposables.CompositeDisposable
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity() {

    val disposables = CompositeDisposable()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        titleTextView.text = javaClass.simpleName
    }

    override fun onDestroy() {
        super.onDestroy()
        disposables.dispose()
    }
}
