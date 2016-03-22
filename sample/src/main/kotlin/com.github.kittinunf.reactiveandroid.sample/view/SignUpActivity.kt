package com.github.kittinunf.reactiveandroid.sample.view

import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.widget.Toast
import com.github.kittinunf.reactiveandroid.rx.addTo
import com.github.kittinunf.reactiveandroid.rx.bindTo
import com.github.kittinunf.reactiveandroid.sample.R
import com.github.kittinunf.reactiveandroid.sample.viewmodel.SignUpViewModel
import com.github.kittinunf.reactiveandroid.sample.viewmodel.SignUpViewModelCommand
import com.github.kittinunf.reactiveandroid.scheduler.AndroidThreadScheduler
import com.github.kittinunf.reactiveandroid.widget.rx_afterTextChanged
import com.github.kittinunf.reactiveandroid.widget.rx_applyAction
import kotlinx.android.synthetic.main.activity_sign_up.*
import rx.subscriptions.CompositeSubscription

class SignUpActivity : AppCompatActivity() {

    val compositeSubscription = CompositeSubscription()

    val initialState = SignUpViewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)

        titleTextView.text = javaClass.simpleName

        val emailChangeCommandObservable = emailEditText.rx_afterTextChanged().map { SignUpViewModelCommand.SignUpEmail(it.toString()) }

        val viewModelObservable = emailChangeCommandObservable.scan(initialState) { state, command -> state.createSignUpViewModel(command) }

        viewModelObservable.subscribe {
            val buttonAction = it.createSignUpAction()
            buttonAction.values.observeOn(AndroidThreadScheduler.mainThreadScheduler)
                    .map{ "You have created account successfully, please check you inbox at $it" }
                    .bindTo(this@SignUpActivity, SignUpActivity::handleSuccess)
                    .addTo(compositeSubscription)

            buttonAction.errors.observeOn(AndroidThreadScheduler.mainThreadScheduler)
                    .bindTo(this@SignUpActivity, SignUpActivity::handleError)
                    .addTo(compositeSubscription)

            signUpButton.rx_applyAction(buttonAction).addTo(compositeSubscription)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        compositeSubscription.unsubscribe()
    }

    fun handleSuccess(message: String) {
        AlertDialog.Builder(this).apply {
            setTitle("Success")
            setMessage(message)
            setPositiveButton("OK", null)
        }.create().show()
    }

    fun handleError(throwable: Throwable) {
        Toast.makeText(this, throwable.message, Toast.LENGTH_SHORT).show()
    }

}