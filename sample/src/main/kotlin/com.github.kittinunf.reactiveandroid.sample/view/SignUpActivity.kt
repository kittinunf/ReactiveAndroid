package com.github.kittinunf.reactiveandroid.sample.view

import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.widget.Toast
import com.github.kittinunf.reactiveandroid.sample.R
import io.reactivex.disposables.CompositeDisposable
import kotlinx.android.synthetic.main.activity_sign_up.titleTextView

class SignUpActivity : AppCompatActivity() {

    val compositeSubscription = CompositeDisposable()

//    val initialState = SignUpViewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)

        titleTextView.text = javaClass.simpleName

//        val emailChangeCommandObservable = emailEditText.rx_afterTextChanged().map { SignUpViewModelCommand.SignUpEmail(it.toString()) }

//        val viewModelObservable = emailChangeCommandObservable.scan(initialState) { state, command -> state.createSignUpViewModel(command) }

//        viewModelObservable.subscribe {
//            val buttonAction = it.createSignUpAction()
//            buttonAction.values.observeOn(AndroidThreadScheduler.main)
//                    .map { "You have created account successfully, please check you inbox at $it" }
//                    .bindTo(this@SignUpActivity, SignUpActivity::handleSuccess)
//                    .addTo(compositeSubscription)

//            buttonAction.errors.observeOn(AndroidThreadScheduler.main)
//                    .bindTo(this@SignUpActivity, SignUpActivity::handleError)
//                    .addTo(compositeSubscription)

//            signUpButton.rx_applyAction(buttonAction).addTo(compositeSubscription)
//        }
    }

    override fun onDestroy() {
        super.onDestroy()
        compositeSubscription.dispose()
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
