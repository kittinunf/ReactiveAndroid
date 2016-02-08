package com.github.kittinunf.reactiveandroid.sample

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.View
import com.github.kittinunf.reactiveandroid.Action
import com.github.kittinunf.reactiveandroid.rx.addTo
import com.github.kittinunf.reactiveandroid.view.rx_visibility
import com.github.kittinunf.reactiveandroid.widget.rx_action
import com.github.kittinunf.reactiveandroid.widget.rx_textChanged
import kotlinx.android.synthetic.main.activity_main.*
import rx.Observable
import rx.subscriptions.CompositeSubscription
import java.util.concurrent.TimeUnit
import java.util.regex.Pattern

class MainActivity : AppCompatActivity() {

    val subscriptions = CompositeSubscription()

    val logInAction by lazy(LazyThreadSafetyMode.NONE) {
        val valid = isFormValid()

        Action(valid) { button: View ->
            Observable.just(Pair(userNameEditText.text.toString(), passwordEditText.text.toString())).delay(5, TimeUnit.SECONDS)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setUpButton()
        setUpProgressBar()
    }

    private fun setUpButton() {
        signInButton.rx_action = logInAction
        signInButton.rx_action!!.values.subscribe { Log.e(javaClass.simpleName, "Success: " + it.toString()) }
    }

    private fun setUpProgressBar() {
        loadingProgressBar.rx_visibility.bindTo(logInAction.executing.map { if (it) View.VISIBLE else View.INVISIBLE }).addTo(subscriptions)
    }

    private fun isFormValid() = Observable.combineLatest(isUsernameValid(), isPasswordValid()) { userValid, passValid ->
        userValid && passValid
    }

    private fun isUsernameValid() = userNameEditText.rx_textChanged().map { Pattern.matches(".+@[a-zA-Z]{2,}\\.[a-zA-Z]{2,}", it.text) }

    private fun isPasswordValid() = passwordEditText.rx_textChanged().map { it.text?.count() ?: 0 >= 6 }

    override fun onDestroy() {
        super.onDestroy()
        subscriptions.unsubscribe()
    }

}