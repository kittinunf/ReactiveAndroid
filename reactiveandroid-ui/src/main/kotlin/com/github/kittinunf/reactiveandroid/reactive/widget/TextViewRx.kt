package com.github.kittinunf.reactiveandroid.reactive.widget

import android.text.Editable
import android.text.TextWatcher
import android.widget.TextView
import com.github.kittinunf.reactiveandroid.ExtensionFieldDelegate
import com.github.kittinunf.reactiveandroid.FieldDelegate
import com.github.kittinunf.reactiveandroid.helper.Tuple4
import com.github.kittinunf.reactiveandroid.internal.AndroidMainThreadDisposable
import com.github.kittinunf.reactiveandroid.reactive.AndroidBindingConsumer
import com.github.kittinunf.reactiveandroid.reactive.Reactive
import io.reactivex.Observable
import io.reactivex.functions.Consumer

var TextView.rx: Reactive<TextView> by FieldDelegate { Reactive(it) }

val Reactive<TextView>.text: Consumer<CharSequence>
    get() = AndroidBindingConsumer(item, binder = { item, text ->
        item.text = text
    })

fun Reactive<TextView>.textChanged(): Observable<Tuple4<CharSequence?, Int, Int, Int>> {
    return Observable.create { s ->
        val listener = item._textChanged
        s.onNext(Tuple4(item.text, 0, 0, 0))
        listener.onTextChanged { charSequence, start, count, after ->
            s.onNext(Tuple4(charSequence, start, count, after))
        }

        s.setDisposable(AndroidMainThreadDisposable({ item.removeTextChangedListener(listener) }))
    }
}

private val TextView._textChanged: _TextView_TextChangedListener
        by ExtensionFieldDelegate({ _TextView_TextChangedListener() }, { addTextChangedListener(it) })

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
