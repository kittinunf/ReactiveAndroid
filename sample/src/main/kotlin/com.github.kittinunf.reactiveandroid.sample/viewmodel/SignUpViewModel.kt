package com.github.kittinunf.reactiveandroid.sample.viewmodel

//sealed class SignUpViewModelCommand {
//    class SignUpEmail(val email: String) : SignUpViewModelCommand()
//}
//
//data class SignUpViewModel(val email: String = "") {
//    fun createSignUpViewModel(command: SignUpViewModelCommand): SignUpViewModel = when (command) {
//        is SignUpViewModelCommand.SignUpEmail -> SignUpViewModel(command.email)
//    }
//
//    fun createSignUpAction(): Action<Unit, String> = Action(emailValidObservable()) { mockSignUpRequest(email) }
//
//    fun emailValidObservable() = Observable.just(email).map { Pattern.matches(".+@[a-zA-Z]{2,}\\.[a-zA-Z]{2,}", it) }
//
//    private fun mockSignUpRequest(username: String): Observable<String> {
//        Log.i(javaClass.simpleName, "$username")
//        return Observable.defer {
//            val r = Random()
//            if (r.nextInt(10) < 3) {
//                Observable.error<String>(RuntimeException("Network failure, please try again.")).delay(1, TimeUnit.SECONDS)
//            } else {
//                Observable.just(username).delay(2, TimeUnit.SECONDS)
//            }
//        }.subscribeOn(Schedulers.computation())
//    }
//}
