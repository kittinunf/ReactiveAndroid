package com.github.kittinunf.reactiveandroid.sample.viewmodel

import android.view.View
import com.github.kittinunf.reactiveandroid.Action
import com.github.kittinunf.reactiveandroid.MutableProperty
import com.github.kittinunf.reactiveandroid.rx.addTo
import com.github.kittinunf.reactiveandroid.rx.bindTo
import com.github.kittinunf.reactiveandroid.scheduler.AndroidThreadScheduler
import rx.Observable
import rx.schedulers.Schedulers
import rx.subscriptions.CompositeSubscription
import java.util.*
import java.util.concurrent.TimeUnit
import java.util.regex.Pattern

interface SignInViewAction {
    fun usernameObservable(): Observable<String>
    fun passwordObservable(): Observable<String>
    fun signInObservable(): Observable<View>

    fun handleSuccess(message: String)
    fun handleFailure(e: Throwable)
}

class SignInViewModel(val viewAction: SignInViewAction) {

    val subscriptions = CompositeSubscription()

    val signInAction by lazy(LazyThreadSafetyMode.NONE) {
        val valid = isFormValid()
        Action(valid) { unit: Unit -> mockSignInRequest(username.value, password.value) }.apply {
            values.observeOn(AndroidThreadScheduler.mainThreadScheduler).map { "You have successfully SignUp, please check your email at ${it.first}" }.bindTo(viewAction, SignInViewAction::handleSuccess)
            errors.observeOn(AndroidThreadScheduler.mainThreadScheduler).bindTo(viewAction, SignInViewAction::handleFailure)
        }
    }

    private val username = MutableProperty("")
    private val password = MutableProperty("")

    private val usernameValid = MutableProperty(false)
    private val passwordValid = MutableProperty(false)

    fun subscribe() {
        username.bindTo(viewAction.usernameObservable()).addTo(subscriptions)
        password.bindTo(viewAction.passwordObservable()).addTo(subscriptions)
        usernameValid.bindTo(isUsernameValid()).addTo(subscriptions)
        passwordValid.bindTo(isPasswordValid()).addTo(subscriptions)
    }

    fun unsubscribe() {
        signInAction.unsubscribe()
        subscriptions.unsubscribe()
    }

    private fun isUsernameValid() = viewAction.usernameObservable().map { Pattern.matches(".+@[a-zA-Z]{2,}\\.[a-zA-Z]{2,}", it) }

    private fun isPasswordValid() = viewAction.passwordObservable().map { it.count() >= 6 }

    private fun isFormValid() = Observable.combineLatest(usernameValid.observable, passwordValid.observable) { u, p -> u && p }

    private fun mockSignInRequest(username: String, password: String): Observable<Pair<String, String>> {
        return Observable.defer {
            val r = Random()
            if (r.nextInt(10) < 3) {
                Observable.error<Pair<String, String>>(RuntimeException("Network failure, please try again.")).delay(1, TimeUnit.SECONDS)
            } else {
                Observable.just(username to password).delay(2, TimeUnit.SECONDS)
            }
        }.subscribeOn(Schedulers.computation())
    }

}

