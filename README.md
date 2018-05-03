# ReactiveAndroid

[ ![Kotlin](https://img.shields.io/badge/Kotlin-1.1.60-blue.svg)](http://kotlinlang.org) [ ![jcenter](https://api.bintray.com/packages/kittinunf/maven/ReactiveAndroid/images/download.svg) ](https://bintray.com/kittinunf/maven/ReactiveAndroid/_latestVersion) [![Build Status](https://travis-ci.org/kittinunf/ReactiveAndroid.svg?branch=master)](https://travis-ci.org/kittinunf/ReactiveAndroid)

Reactive events and properties with RxJava for Android 

## Installation

### Dependency

* [RxJava](https://github.com/ReactiveX/RxJava)

### Gradle

``` Groovy
repositories {
    jcenter()
}

dependencies {
    compile 'com.github.kittinunf.reactiveandroid:reactiveandroid-ui:<latest-version>' //for base UI
    compile 'com.github.kittinunf.reactiveandroid:reactiveandroid-appcompat-v7:<latest-version>' //for appcompat-v7 module
    compile 'com.github.kittinunf.reactiveandroid:reactiveandroid-support-v4:<latest-version>' //for support-v4 module
    compile 'com.github.kittinunf.reactiveandroid:reactiveandroid-design:<latest-version>' //for design support module
}
```

## Why you might need this?

Have you ever wish Android SDK had supported RxJava by default? Well, you have that wish with `ReactiveAndroid`.

Let's say, you need to observe view being clicked. Yes, you definitely can do it with [View.OnClickListener](https://developer.android.com/reference/android/view/View.OnClickListener.html).
However, with `ReactiveAndroid`, you can simply do this:

``` Kotlin
button.rx.click().subscribe {
    //do something when button is clicked
}
```

Or, let's assume that you have `Observable<String>` that wants to be outputted with TextView. You can just do this:

``` Kotlin
val o = Observable.just("Hello")
o.map { "$it Bob" }.subscribe(textView.rx.text)
```

From above example, whenever `Observable` emits next, it will bind with `setText(value)` of TextView.

In this way, you could construct your logic as Observable<T> in Presenter or ViewModel, then bind it with your view to make it updated.

This makes `ReactiveAndroid` a powerful tool to perform data binding in your MV* architecture (MVP, MVVM). 

Another example is registration form. You probably want to know whether input email & password are valid or not. So that, you could disable/enable button as user types.

``` Kotlin
val emails = emailEditText.rx.text().map { it.toString() } // becomes Observable<String> for email
val passwords = passwordEditText.rx.text().map { it.toString() } // becomes Observable<String> for password

val emailValids = emails.map { Pattern.matches(EMAIL_PATTERN, it) }
val passwordValids = passwords.map { it.length > PASSWORD_LENGTH }

val emailAndPasswordValids = Observables.combineLatest(emailValids, passwordValids) { user, pass -> user and pass } // becomes Observable<Boolean> for validity

emailAndPasswordValids.bindTo(signInButton.rx.enabled) // bind validity value with button
```

## Sample

There are couple of sample code that take advantage power of `ReactiveAndroid`. You can checkout in `sample` folder.

## Terminology

We want to be familiar as much as possible to the Android SDK. So that `Event` and `Property` from Android elements follow naming convention of Android SDK whenever it can.

### Events

Think about listener from Android SDK of your choice, then remove the word "on" and also the word "listener". Then, append that "name" after `rx_`. 

For example, 
* `View.OnClickListener` => `view.rx.click()`
* `RatingBar.OnRatingBarChangeListener` => `ratingBar.rx_ratingBarChange()`
* `MenuItem.OnMenuItemClickListener` => `menuItem.menuItemClick()` etc.

### Properties

Think about name of property of UI element from Android SDK. Remove {set|get|is|has}. Then, append after `rx_` in the similar fashion as Events.

For example, 
* `View.setEnabled/isEnabled` => `view.rx.enabled`
* `DatePicker.setMinDate/getMinDate` => `datePicker.rx_minDate`
* `RecyclerView.setHasFixedSize/hasFixedSize` => `recyclerView.rx_hasFixedSize` etc.

## Credits

ReactiveAndroid is brought to you by [contributors](https://github.com/kittinunf/ReactiveAndroid/graphs/contributors).

## Licenses

ReactiveAndroid is released under the [MIT](http://opensource.org/licenses/MIT) license.
