package com.github.kittinunf.reactiveandroid.widget

import android.text.Editable
import android.text.TextWatcher
import android.widget.TextView
import com.github.kittinunf.reactiveandroid.EditorActionListener
import com.github.kittinunf.reactiveandroid.TextChangedListener
import com.github.kittinunf.reactiveandroid.subscription.AndroidMainThreadSubscription
import rx.Observable

//================================================================================
// Events
//================================================================================

fun TextView.rx_editorAction(consumed: Boolean): Observable<EditorActionListener> {
    return Observable.create { subscriber ->
        setOnEditorActionListener { textView, actionId, event ->
            subscriber.onNext(EditorActionListener(textView, actionId, event))
            consumed
        }
        
        subscriber.add(AndroidMainThreadSubscription {
            setOnEditorActionListener(null)
        })
    }
}

fun TextView.rx_textChanged(): Observable<TextChangedListener> {
    return Observable.create { subscriber ->
        val listener = object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                subscriber.onNext(TextChangedListener(s, start, before, count))
            }
        }

        addTextChangedListener(listener)

        subscriber.add(AndroidMainThreadSubscription {
            removeTextChangedListener(listener)
        })
    }
}


