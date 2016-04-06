# ReactiveAndroid

[ ![Kotlin](https://img.shields.io/badge/Kotlin-1.0.0-blue.svg)](http://kotlinlang.org) [ ![jcenter](https://api.bintray.com/packages/kittinunf/maven/ReactiveAndroid/images/download.svg) ](https://bintray.com/kittinunf/maven/ReactiveAndroid/_latestVersion) [![Build Status](https://travis-ci.org/kittinunf/ReactiveAndroid.svg?branch=master)](https://travis-ci.org/kittinunf/ReactiveAndroid)

Reactive events and properties with RxJava for Android 

## Installation

### Gradle

``` Groovy
repositories {
    jcenter()
}

dependencies {
    compile 'com.github.kittinunf.reactiveandroid:reactiveandroid-ui:0.2.8' //for base UI
    compile 'com.github.kittinunf.reactiveandroid:reactiveandroid-appcompat-v7:0.2.8' //for appcompat-v7 module
    compile 'com.github.kittinunf.reactiveandroid:reactiveandroid-support-v4:0.2.8' //for support-v4 module
    compile 'com.github.kittinunf.reactiveandroid:reactiveandroid-design:0.2.8' //for design support module
}
```

## Why you might need this?

Have you ever wish Android SDK had supported RxJava by default? Well, you have it there with `ReactiveAndroid`.
Let's say, you need to observe view being clicked. Yes, you definitely can do it with [View.OnClickListener](https://developer.android.com/reference/android/view/View.OnClickListener.html).
However, with `ReactiveAndroid`, you can simply do this:

``` Kotlin
button.rx_click().subscribe {
    //do something when button is clicked
}
```

Or, let's assume that you have `Observable<String>` that wants to be outputted with TextView. You can just do this:

``` Kotlin
val o = Observable.just("Hello")
o.map { "$it Bob" }.bindTo(textView.rx_text)
```

From above example, whenever `Observable` emits next, `bindTo` will bind with `setText(value)` / `getText()` of TextView. 
This makes it easy to perform data binding with help of RxJava.

Another example is register form. You probably want to know whether input email & input password are valid or not.

``` Kotlin
val emails = emailEditText.rx_afterTextChanged().map { it.toString() } // becomes Observable<String> for email
val passwords = passwordEditText.rx_afterTextChanged().map { it.toString() } // becomes Observable<String> for password

val emailValids = emails.map { Pattern.matches(EMAIL_PATTERN, it) }
val passwordValids = passwords.map { it.length > PASSWORD_LENGTH }

val emailAndPasswordValids = Observable.combineLatest(emailValids, passwordValids) { user, pass -> user and pass } // becomes Observable<Boolean> for validity

emailAndPasswordValids.bindTo(signInButton.rx_enabled) // bind validity value with button
```

## Sample

There are couple of sample code that take advantage power of `ReactiveAndroid`. You can checkout in `sample` folder.

## Terminology

As we want to be familiar as much as possible to the Android SDK. Events from Android UI try to follow naming convention of Android SDK as much as possible. 

### Events

You should think about listener from Android SDK of your choice, then remove the word "on" and also the word "listener". Then, append the after `rx_`. 

For example, View.OnClickListener => view.rx_click(), RatingBar.OnRatingBarChangeListener => ratingBar.rx_ratingBarChange(), MenuItem.OnMenuItemClickListener => menuItem.menuItemClick() etc.

### Properties

Think about name of property of UI element from Android SDK. Remove {set|get|is|has}. Then, append after `rx_`.

For example, View.setEnabled/isEnabled => view.rx_enabled, DatePicker.setMinDate/getMinDate => datePicker.rx_minDate, RecyclerView.setHasFixedSize/hasFixedSize => recyclerView.rx_hasFixedSize

## Credits

ReactiveAndroid is brought to you by [contributors](https://github.com/kittinunf/ReactiveAndroid/graphs/contributors).

## Licenses

ReactiveAndroid is released under the [MIT](http://opensource.org/licenses/MIT) license.


