package com.github.kittinunf.reactiveandroid.sample

import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.Toast
import com.github.kittinunf.reactiveandroid.Action
import com.github.kittinunf.reactiveandroid.Property
import com.github.kittinunf.reactiveandroid.rx.addTo
import com.github.kittinunf.reactiveandroid.rx.lift
import com.github.kittinunf.reactiveandroid.scheduler.AndroidThreadScheduler
import com.github.kittinunf.reactiveandroid.view.rx_visibility
import com.github.kittinunf.reactiveandroid.widget.rx_action
import com.github.kittinunf.reactiveandroid.widget.rx_itemsWith
import com.github.kittinunf.reactiveandroid.widget.rx_textChanged
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.spinner_item.view.*
import rx.Observable
import rx.subscriptions.CompositeSubscription
import java.util.*
import java.util.concurrent.TimeUnit
import java.util.regex.Pattern

class MainActivity : AppCompatActivity() {

    val subscriptions = CompositeSubscription()

    val logInAction by lazy(LazyThreadSafetyMode.NONE) {
        val valid = isFormValid()
        Action(valid) { unit: Unit -> mockNetworkRequest() }.apply {
            values.observeOn(AndroidThreadScheduler.mainThreadScheduler).lift(this@MainActivity, MainActivity::handleSuccess).addTo(subscriptions)
            errors.observeOn(AndroidThreadScheduler.mainThreadScheduler).lift(this@MainActivity, MainActivity::handleFailure).addTo(subscriptions)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        title = ""

        setUpToolbar()
        setUpButton()
        setUpProgressBar()
    }

    private fun setUpToolbar() {
        setSupportActionBar(toolbar)

        val property = Property(listOf("Item 1", "Item 2", "Item 3"))
        toolbarSpinner.rx_itemsWith(property.observable, { position, item, convertView, parent ->
            val view = LayoutInflater.from(this@MainActivity).inflate(R.layout.spinner_item, parent, false)
            view.spinnerTextView.text = item
            view
        }, { position, item -> position.toLong() })
    }

    override fun onDestroy() {
        super.onDestroy()
        logInAction.unsubscribe()
        subscriptions.unsubscribe()
    }

    private fun setUpButton() {
        signInButton.rx_action = logInAction
    }

    private fun setUpProgressBar() {
        loadingProgressBar.rx_visibility.bindTo(logInAction.executing.map { if (it) View.VISIBLE else View.INVISIBLE }).addTo(subscriptions)
    }

    private fun isFormValid() = Observable.combineLatest(isUsernameValid(), isPasswordValid()) { userValid, passValid ->
        userValid && passValid
    }

    private fun isUsernameValid() = userNameEditText.rx_textChanged().map { Pattern.matches(".+@[a-zA-Z]{2,}\\.[a-zA-Z]{2,}", it.text) }

    private fun isPasswordValid() = passwordEditText.rx_textChanged().map { it.text?.count() ?: 0 >= 6 }

    private fun mockNetworkRequest(): Observable<Pair<String, String>> {
        return Observable.defer {
            val r = Random()
            //about 30% failure
            val i = r.nextInt(10)
            Log.d(javaClass.simpleName, i.toString())
            if (i < 3) {
                Observable.error<Pair<String, String>>(RuntimeException("Network failure, please try again."))
            } else {
                Observable.just(Pair(userNameEditText.text.toString(), passwordEditText.text.toString())).delay(3, TimeUnit.SECONDS)
            }
        }
    }

    private fun handleSuccess() {
        AlertDialog.Builder(this).apply {
            setTitle("Success")
            setMessage("Log In successfully")
        }.create().show()
    }

    private fun handleFailure(e: Throwable) {
        Toast.makeText(this, e.message, Toast.LENGTH_SHORT).show()
    }

}