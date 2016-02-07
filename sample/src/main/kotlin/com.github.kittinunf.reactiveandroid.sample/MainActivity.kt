package com.github.kittinunf.reactiveandroid.sample

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.View
import com.github.kittinunf.reactiveandroid.Action
import com.github.kittinunf.reactiveandroid.widget.rx_action
import com.github.kittinunf.reactiveandroid.widget.rx_textChanged
import kotlinx.android.synthetic.main.activity_main.*
import rx.Observable
import rx.subscriptions.CompositeSubscription
import java.util.concurrent.TimeUnit
import java.util.regex.Pattern

class MainActivity : AppCompatActivity() {

    val subscriptions = CompositeSubscription()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        signInButton.rx_action = logInAction()
        signInButton.rx_action!!.values.subscribe { Log.e(javaClass.simpleName, "Success: " + it.toString())  }
    }

    fun isFormValid(): Observable<Boolean> {
        return Observable.combineLatest(isUsernameValid(), isPasswordValid(), { user, pass ->
            user && pass
        })
    }

    fun isUsernameValid(): Observable<Boolean> {
        return userNameEditText.rx_textChanged().map { Pattern.matches(".+@[a-zA-Z]{2,}\\.[a-zA-Z]{2,}", it.text) }
    }

    fun isPasswordValid(): Observable<Boolean> {
        return passwordEditText.rx_textChanged().map { it.text?.count() ?: 0 >= 6 }
    }

    fun logInAction(): Action<View, Pair<String, String>> {
        val valid = isFormValid()
        return Action(valid) { button: View ->
            Observable.just(Pair(userNameEditText.text.toString(), passwordEditText.text.toString())).delay(5, TimeUnit.SECONDS)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        subscriptions.unsubscribe()
    }

}