package com.github.kittinunf.reactiveandroid.sample.viewmodel

import com.github.kittinunf.reactiveandroid.MutableProperty
import io.reactivex.Observable

interface SignInViewAction {
    fun usernameObservable(): Observable<String>
    fun passwordObservable(): Observable<String>

    fun username(): MutableProperty<CharSequence>
    fun password(): MutableProperty<CharSequence>
}
//
//class SignInViewModel(val viewAction: SignInViewAction) {
//
//    val signInAction by lazy(LazyThreadSafetyMode.NONE) {
//        Action(formValidObservable) { unit: Unit -> mockSignInRequest(viewAction.username().value.toString(), viewAction.password().value.toString()) }
//    }
//
//    val usernameAndPasswordObservable: Observable<Pair<String, String>>
//    val formValidObservable: Observable<Boolean>
//
//    init {
//        usernameAndPasswordObservable = Observable.combineLatest(viewAction.usernameObservable(), viewAction.passwordObservable()) { u, p -> u to p }
//
//        val usernameValidObservable = viewAction.usernameObservable().map { Pattern.matches(".+@[a-zA-Z]{2,}\\.[a-zA-Z]{2,}", it) }
//        val passwordValidObservable = viewAction.passwordObservable().map { it.count() >= 6 }
//
//        formValidObservable = Observable.combineLatest(usernameValidObservable, passwordValidObservable) { u, p -> u and p }
//    }
//
//    private fun mockSignInRequest(username: String, password: String): Observable<Pair<String, String>> {
//        Log.i(javaClass.simpleName, "$username : $password")
//        return Observable.defer {
//            val r = Random()
//            if (r.nextInt(10) < 3) {
//                Observable.error<Pair<String, String>>(RuntimeException("Network failure, please try again.")).delay(1, TimeUnit.SECONDS)
//            } else {
//                Observable.just(username to password).delay(2, TimeUnit.SECONDS)
//            }
//        }.subscribeWithOn(Schedulers.computation())
//    }
//
//}

