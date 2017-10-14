package com.github.kittinunf.reactiveandroid.sample.view

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.inputmethod.EditorInfo
import android.widget.Toast
import com.github.kittinunf.reactiveandroid.reactive.addTo
import com.github.kittinunf.reactiveandroid.reactive.widget.click
import com.github.kittinunf.reactiveandroid.reactive.widget.onEditorAction
import com.github.kittinunf.reactiveandroid.reactive.widget.rx
import com.github.kittinunf.reactiveandroid.reactive.widget.textChanged

import com.github.kittinunf.reactiveandroid.sample.R
import com.github.kittinunf.reactiveandroid.view.enabled
import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.functions.BiFunction
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity() {

    val disposables = CompositeDisposable()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        titleTextView.text = javaClass.simpleName

        val loginValid = Observable.combineLatest(
                userNameEditText.rx.textChanged().map { it.s.toString() },
                passwordEditText.rx.textChanged().map { it.s.toString() },
                BiFunction { username: String, password: String ->
                    username.isNotEmpty() && password.isNotEmpty()
                })

        val loginSubmit = Observable.merge(
                passwordEditText.rx.onEditorAction(true).filter { it.actionId == EditorInfo.IME_ACTION_DONE }.map { Unit },
                loginButton.rx.click().map { Unit })

        loginValid.subscribe(loginButton.rx.enabled).addTo(disposables)

        loginSubmit.subscribe {
            Toast.makeText(this, "Submit Login", Toast.LENGTH_SHORT).show()
        }.addTo(disposables)

    }

    override fun onDestroy() {
        super.onDestroy()
        disposables.dispose()
    }
}
