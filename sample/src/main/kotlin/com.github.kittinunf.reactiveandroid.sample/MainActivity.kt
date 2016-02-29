package com.github.kittinunf.reactiveandroid.sample

import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.github.kittinunf.reactiveandroid.Action
import com.github.kittinunf.reactiveandroid.ConstantProperty
import com.github.kittinunf.reactiveandroid.rx.addTo
import com.github.kittinunf.reactiveandroid.rx.bindTo
import com.github.kittinunf.reactiveandroid.rx.lift
import com.github.kittinunf.reactiveandroid.scheduler.AndroidThreadScheduler
import com.github.kittinunf.reactiveandroid.view.rx_focusChange
import com.github.kittinunf.reactiveandroid.view.rx_visibility
import com.github.kittinunf.reactiveandroid.widget.*
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.spinner_item.view.*
import kotlinx.android.synthetic.main.spinner_item_dropdown.view.*
import rx.Observable
import rx.schedulers.Schedulers
import rx.subscriptions.CompositeSubscription
import java.util.*
import java.util.concurrent.TimeUnit
import java.util.regex.Pattern

class MainActivity : AppCompatActivity() {

    val subscriptions = CompositeSubscription()

    val signInAction by lazy(LazyThreadSafetyMode.NONE) {
        val valid = isFormValid()
        Action(valid) { unit: Unit -> mockSignInRequest(userNameEditText.text.toString(), passwordEditText.text.toString()) }.apply {
            values.observeOn(AndroidThreadScheduler.mainThreadScheduler).map { "Sign In" }.lift(this@MainActivity, MainActivity::handleSuccess).addTo(subscriptions)
            errors.observeOn(AndroidThreadScheduler.mainThreadScheduler).lift(this@MainActivity, MainActivity::handleFailure).addTo(subscriptions)
        }
    }

    val signUpAction by lazy(LazyThreadSafetyMode.NONE) {
        val valid = isEmailValid()
        Action(valid) { unit: Unit -> mockSignUpRequest(emailEditText.text.toString()) }.apply {
            values.observeOn(AndroidThreadScheduler.mainThreadScheduler).map { "Sign Up" }.lift(this@MainActivity, MainActivity::handleSuccess).addTo(subscriptions)
            errors.observeOn(AndroidThreadScheduler.mainThreadScheduler).lift(this@MainActivity, MainActivity::handleSignUpFailure).addTo(subscriptions)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        title = ""

        setUpToolbar()
        setUpTextInputLayout()
        setUpButton()
        setUpProgressBar()
    }

    override fun onDestroy() {
        super.onDestroy()
        signInAction.unsubscribe()
        signUpAction.unsubscribe()
        subscriptions.unsubscribe()
    }

    private fun setUpToolbar() {
        setSupportActionBar(toolbar)

        val property = ConstantProperty(listOf("Item 1", "Item 2", "Item 3"))
        toolbarSpinner.rx_itemsWith(property.observable, ItemAdapter()).addTo(subscriptions)
    }

    private fun setUpTextInputLayout() {
        val emailValid = isEmailValid()
        val emailFocusChange = emailEditText.rx_focusChange().map { it.hasFocus }

        Observable.combineLatest(emailValid, emailFocusChange) { isValid, hasFocus ->
            if (!hasFocus) return@combineLatest "Email"
            if (isValid) "OK" else "Please enter valid email"
        }.bindTo(emailTextInputLayout.rx_hint).addTo(subscriptions)

        emailEditText.rx_textChanged().map { false }.bindTo(emailTextInputLayout.rx_errorEnabled).addTo(subscriptions)
    }

    private fun setUpButton() {
        signInButton.rx_action = signInAction
        signUpButton.rx_action = signUpAction
    }

    private fun setUpProgressBar() {
        loadingProgressBar.rx_visibility.bindTo(signInAction.executing.map { if (it) View.VISIBLE else View.INVISIBLE }).addTo(subscriptions)
    }

    private fun isFormValid() = Observable.combineLatest(isUsernameValid(), isPasswordValid()) { userValid, passValid ->
        userValid && passValid
    }

    private fun isUsernameValid() = userNameEditText.rx_textChanged().map { Pattern.matches(".+@[a-zA-Z]{2,}\\.[a-zA-Z]{2,}", it.text) }

    private fun isPasswordValid() = passwordEditText.rx_textChanged().map { it.text?.count() ?: 0 >= 6 }

    private fun isEmailValid() = emailEditText.rx_textChanged().map { Pattern.matches(".+@[a-zA-Z]{2,}\\.[a-zA-Z]{2,}", it.text) }

    private fun mockSignInRequest(username: String, password: String): Observable<Pair<String, String>> {
        return Observable.defer {
            val r = Random()
            if (r.nextInt(10) < 3) {
                Observable.error<Pair<String, String>>(RuntimeException("Network failure, please try again."))
            } else {
                Observable.just(username to password).delay(2, TimeUnit.SECONDS)
            }
        }.subscribeOn(Schedulers.io())
    }

    private fun mockSignUpRequest(email: String): Observable<String> {
        return Observable.defer {
            val r = Random()
            if (r.nextInt(10) > 3) {
                Observable.error<String>(RuntimeException("Network failure, please try again."))
            } else {
                Observable.just(email).delay(2, TimeUnit.SECONDS)
            }
        }.subscribeOn(Schedulers.io())
    }

    private fun handleSuccess(action: String) {
        emailTextInputLayout.isErrorEnabled = false

        AlertDialog.Builder(this).apply {
            setTitle("Success")
            setMessage("$action successfully")
        }.create().show()
    }

    private fun handleFailure(e: Throwable) {
        Toast.makeText(this, e.message, Toast.LENGTH_SHORT).show()
    }

    private fun handleSignUpFailure(e: Throwable) {
        emailTextInputLayout.error = e.message
    }

    inner class ItemAdapter : AdapterViewProxyAdapter<String>() {
        override fun getItemId(position: Int) = position.toLong()

        override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View? {
            val view = convertView ?: LayoutInflater.from(this@MainActivity).inflate(R.layout.spinner_item, parent, false)
            view.spinnerTextView.text = this[position]
            return view
        }

        override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup?): View? {
            val view = convertView ?: LayoutInflater.from(this@MainActivity).inflate(R.layout.spinner_item_dropdown, parent, false)
            view.spinnerTextViewDropdown.text = this[position]
            return view
        }

    }
}


