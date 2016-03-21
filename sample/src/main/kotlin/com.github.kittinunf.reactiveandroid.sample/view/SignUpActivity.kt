package com.github.kittinunf.reactiveandroid.sample.view

import android.support.v7.app.AppCompatActivity

class SignUpActivity : AppCompatActivity() {

//    val signUpAction by lazy(LazyThreadSafetyMode.NONE) {
//        val valid = isEmailValid()
//        Action(valid) { unit: Unit -> mockSignUpRequest(emailEditText.text.toString()) }.apply {
//            values.observeOn(AndroidThreadScheduler.mainThreadScheduler).map { "Sign Up" }.bindTo(this@SignInActivity, SignInActivity::handleSuccess).addTo(subscriptions)
//            errors.observeOn(AndroidThreadScheduler.mainThreadScheduler).bindTo(this@SignInActivity, SignInActivity::handleSignUpFailure).addTo(subscriptions)
//        }
//    }
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//
//        setUpTextInputLayout()
//    }
//
//    override fun onStart() {
//        super.onStart()
//        signUpButton.rx_applyAction(signUpAction).addTo(subscriptions)
//    }
//
//    private fun setUpTextInputLayout() {
//        val emailValid = isEmailValid()
//        val emailFocusChange = emailEditText.rx_focusChange().map { it.hasFocus }
//
//        Observable.combineLatest(emailValid, emailFocusChange) { isValid, hasFocus ->
//            if (!hasFocus) return@combineLatest "Email"
//            if (isValid) "OK" else "Please enter valid email"
//        }.bindTo(emailTextInputLayout.rx_hint).addTo(subscriptions)
//
//        emailEditText.rx_textChanged().map { false }.bindTo(emailTextInputLayout.rx_errorEnabled).addTo(subscriptions)
//    }
//
//    private fun handleSignUpSuccess() {
//        emailTextInputLayout.isErrorEnabled = false
//    }
//
//    private fun handleSignUpFailure(e: Throwable) {
//        emailTextInputLayout.error = e.message
//    }
//
//    private fun isEmailValid() = emailEditText.rx_textChanged().map { Pattern.matches(".+@[a-zA-Z]{2,}\\.[a-zA-Z]{2,}", it.text) }
//
//    private fun mockSignUpRequest(email: String): Observable<String> {
//        return Observable.defer {
//            val r = Random()
//            if (r.nextInt(10) > 3) {
//                Observable.error<String>(RuntimeException("Network failure, please try again."))
//            } else {
//                Observable.just(email).delay(2, TimeUnit.SECONDS)
//            }
//        }.subscribeOn(Schedulers.io())
//    }

}