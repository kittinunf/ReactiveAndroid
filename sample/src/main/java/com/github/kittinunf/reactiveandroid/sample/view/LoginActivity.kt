package com.github.kittinunf.reactiveandroid.sample.view

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.Toast
import com.github.kittinunf.reactiveandroid.helper.Observables
import com.github.kittinunf.reactiveandroid.reactive.addTo
import com.github.kittinunf.reactiveandroid.reactive.view.click
import com.github.kittinunf.reactiveandroid.reactive.view.enabled
import com.github.kittinunf.reactiveandroid.reactive.widget.onEditorAction
import com.github.kittinunf.reactiveandroid.reactive.widget.rx
import com.github.kittinunf.reactiveandroid.reactive.widget.text
import com.github.kittinunf.reactiveandroid.sample.R
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import kotlinx.android.synthetic.main.activity_login.loadingProgressBar
import kotlinx.android.synthetic.main.activity_login.loginButton
import kotlinx.android.synthetic.main.activity_login.passwordEditText
import kotlinx.android.synthetic.main.activity_login.titleTextView
import kotlinx.android.synthetic.main.activity_login.userNameEditText
import java.util.concurrent.TimeUnit

class LoginActivity : AppCompatActivity() {

    val disposables = CompositeDisposable()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        titleTextView.text = javaClass.simpleName

        val loginValid = Observables.combineLatest(
                userNameEditText.rx.text(),
                passwordEditText.rx.text()) { username, password ->
            username.isNotEmpty() && password.isNotEmpty()
        }

        val loginSubmit = Observable.merge(
                passwordEditText.rx.onEditorAction(true).filter { it.actionId == EditorInfo.IME_ACTION_DONE }.map { Unit },
                loginButton.rx.click().map { Unit })

        loginValid.subscribe(loginButton.rx.enabled)
                .addTo(disposables)

        loginSubmit
                .doOnNext { loadingProgressBar.visibility = View.VISIBLE }
                .flatMap { fakeNetwork() }
                .observeOn(AndroidSchedulers.mainThread())
                .doOnNext { loadingProgressBar.visibility = View.GONE }
                .subscribe { Toast.makeText(this, "Login successful", Toast.LENGTH_SHORT).show() }
                .addTo(disposables)

        userNameEditText.rx.onEditorAction().mergeWith(passwordEditText.rx.onEditorAction()).subscribe {
            Log.d(javaClass.simpleName, it.toString())
        }
    }

    private fun fakeNetwork(): Observable<Boolean> {
        return Observable.just(true)
                .delay(500, TimeUnit.MILLISECONDS)
    }

    override fun onDestroy() {
        super.onDestroy()
        disposables.dispose()
    }
}
