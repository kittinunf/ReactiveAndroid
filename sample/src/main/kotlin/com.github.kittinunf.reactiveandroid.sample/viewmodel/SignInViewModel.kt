package com.github.kittinunf.reactiveandroid.sample.viewmodel

import android.util.Log
import com.github.kittinunf.reactiveandroid.Action
import com.github.kittinunf.reactiveandroid.MutableProperty
import rx.Observable
import rx.schedulers.Schedulers
import java.util.*
import java.util.concurrent.TimeUnit
import java.util.regex.Pattern

interface SignInViewAction {
    fun usernameObservable(): Observable<String>
    fun passwordObservable(): Observable<String>

    fun username(): MutableProperty<String>
    fun password(): MutableProperty<String>
}

class SignInViewModel(val viewAction: SignInViewAction) {

    val signInAction by lazy(LazyThreadSafetyMode.NONE) {
        Action(formValidObservable) { unit: Unit -> mockSignInRequest(viewAction.username().value, viewAction.password().value) }
    }

    val usernameAndPasswordObservable: Observable<Pair<String, String>>
    val formValidObservable: Observable<Boolean>

    init {
        usernameAndPasswordObservable = Observable.combineLatest(viewAction.usernameObservable(), viewAction.passwordObservable()) { u, p -> u to p }

        val usernameValidObservable = viewAction.usernameObservable().map { Pattern.matches(".+@[a-zA-Z]{2,}\\.[a-zA-Z]{2,}", it) }
        val passwordValidObservable = viewAction.passwordObservable().map { it.count() >= 6 }

        formValidObservable = Observable.combineLatest(usernameValidObservable, passwordValidObservable) { u, p -> u && p }
    }

    private fun mockSignInRequest(username: String, password: String): Observable<Pair<String, String>> {
        Log.i("SignInViewModel", "$username : $password")
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

