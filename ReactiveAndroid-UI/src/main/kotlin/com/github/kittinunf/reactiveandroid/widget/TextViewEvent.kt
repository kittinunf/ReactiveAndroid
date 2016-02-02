package com.github.kittinunf.reactiveandroid.widget

import android.text.Editable
import android.text.TextWatcher
import android.view.KeyEvent
import android.widget.TextView
import com.github.kittinunf.reactiveandroid.subscription.AndroidMainThreadSubscription
import rx.Observable

//================================================================================
// Events
//================================================================================

data class EditorActionListener(val textView: TextView, val actionId: Int, val event: KeyEvent)

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

fun TextView.rx_afterTextChanged(): Observable<Editable> {
    return Observable.create { subscriber ->
        _textChanged.afterTextChanged {
            subscriber.onNext(it)
        }

        subscriber.add(AndroidMainThreadSubscription {
            removeTextChangedListener(_textChanged)
        })
    }
}

data class BeforeTextChangedListener(val text: CharSequence?, val start: Int, val count: Int, val after: Int)

fun TextView.rx_beforeTextChanged(): Observable<BeforeTextChangedListener> {
    return Observable.create { subscriber ->
        _textChanged.beforeTextChanged { charSequence, start, count, after ->
            subscriber.onNext(BeforeTextChangedListener(charSequence, start, count, after))
        }

        subscriber.add(AndroidMainThreadSubscription {
            removeTextChangedListener(_textChanged)
        })
    }
}

data class TextChangedListener(val text: CharSequence?, val start: Int, val count: Int, val after: Int)

fun TextView.rx_textChanged(): Observable<TextChangedListener> {
    return Observable.create { subscriber ->
        _textChanged.onTextChanged { charSequence, start, count, after ->
            subscriber.onNext(TextChangedListener(charSequence, start, count, after))
        }

        subscriber.add(AndroidMainThreadSubscription {
            removeTextChangedListener(_textChanged)
        })
    }
}

private val TextView._textChanged: _TextView_TextChangedListener
    get () {
        val listener = _TextView_TextChangedListener()
        addTextChangedListener(listener)
        return listener
    }

private class _TextView_TextChangedListener : TextWatcher {

    private var afterTextChanged: ((Editable?) -> Unit)? = null

    private var beforeTextChanged: ((CharSequence?, Int, Int, Int) -> Unit)? = null

    private var onTextChanged: ((CharSequence?, Int, Int, Int) -> Unit)? = null

    override fun afterTextChanged(s: Editable?) {
        afterTextChanged?.invoke(s)
    }

    fun afterTextChanged(listener: ((Editable?) -> Unit)) {
        afterTextChanged = listener
    }

    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
        beforeTextChanged?.invoke(s, start, count, after)
    }

    fun beforeTextChanged(listener: (CharSequence?, Int, Int, Int) -> Unit) {
        beforeTextChanged = listener
    }

    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
        onTextChanged?.invoke(s, start, before, count)
    }

    fun onTextChanged(listener: (CharSequence?, Int, Int, Int) -> Unit) {
        onTextChanged = listener
    }

}


