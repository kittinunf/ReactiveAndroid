package com.github.kittinunf.reactiveandroid.reactive.widget

import android.content.res.ColorStateList
import android.graphics.Typeface
import android.text.Editable
import android.text.TextWatcher
import android.view.KeyEvent
import android.widget.TextView
import com.github.kittinunf.reactiveandroid.ExtensionFieldDelegate
import com.github.kittinunf.reactiveandroid.FieldDelegate
import com.github.kittinunf.reactiveandroid.internal.AndroidMainThreadDisposable
import com.github.kittinunf.reactiveandroid.reactive.AndroidBindingConsumer
import com.github.kittinunf.reactiveandroid.reactive.Reactive
import com.github.kittinunf.reactiveandroid.reactive.ofType
import io.reactivex.Observable
import io.reactivex.functions.Consumer

var TextView.rx: Reactive<TextView> by FieldDelegate { Reactive(it) }

//Properties

val Reactive<TextView>.text: Consumer<CharSequence>
    get() = AndroidBindingConsumer(item, binder = { item, text ->
        item.text = text
    })

val Reactive<TextView>.error: Consumer<CharSequence>
    get() = AndroidBindingConsumer(item, binder = { item, error ->
        item.error = error
    })

val Reactive<TextView>.hint: Consumer<CharSequence>
    get() = AndroidBindingConsumer(item, binder = { item, hint ->
        item.hint = hint
    })

val Reactive<TextView>.textColor: Consumer<Int>
    get() = AndroidBindingConsumer(item, binder = { item, color ->
        item.setTextColor(color)
    })

val Reactive<TextView>.textColors: Consumer<ColorStateList>
    get() = AndroidBindingConsumer(item, binder = { item, colors ->
        item.setTextColor(colors)
    })

val Reactive<TextView>.typeface: Consumer<Typeface>
    get() = AndroidBindingConsumer(item, binder = { item, typeface ->
        item.typeface = typeface
    })

//Listeners

sealed class TextWatcherEvent {
    data class AfterTextChanged(val s: CharSequence?) : TextWatcherEvent()
    data class OnTextChanged(val s: CharSequence?, val start: Int, val before: Int, val count: Int) : TextWatcherEvent()
    data class BeforeTextChanged(val s: CharSequence?, val start: Int, val count: Int, val after: Int) : TextWatcherEvent()
}

fun Reactive<TextView>.onTextChanged() = textWatcher().ofType<TextWatcherEvent.OnTextChanged>()

fun Reactive<TextView>.beforeTextChanged() = textWatcher().ofType<TextWatcherEvent.BeforeTextChanged>()

fun Reactive<TextView>.afterTextChanged() = textWatcher().ofType<TextWatcherEvent.AfterTextChanged>()

private fun Reactive<TextView>.textWatcher(): Observable<TextWatcherEvent> =
        Observable.create<TextWatcherEvent> { emitter ->
            emitter.onNext(TextWatcherEvent.OnTextChanged(item.text, 0, 0, 0))
            val listener = item._textChanged

            listener.apply {
                afterTextChanged {
                    emitter.onNext(TextWatcherEvent.AfterTextChanged(it))
                }
                beforeTextChanged { charSequence, start, before, count ->
                    emitter.onNext(TextWatcherEvent.BeforeTextChanged(charSequence, start, before, count))
                }

                onTextChanged { charSequence, start, count, after ->
                    emitter.onNext(TextWatcherEvent.OnTextChanged(charSequence, start, count, after))
                }
            }

            emitter.setDisposable(AndroidMainThreadDisposable({ item.removeTextChangedListener(listener) }))
        }.share()

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

data class EditorActionEvent(val textView: TextView, val actionId: Int, val event: KeyEvent)

private val TextView._editActionListener: _TextView_EditorActionListener
        by ExtensionFieldDelegate({ _TextView_EditorActionListener() }, { setOnEditorActionListener(it) })

fun Reactive<TextView>.onEditorAction(consumed: Boolean = true): Observable<EditorActionEvent> =
        Observable.create { s ->
            item._editActionListener.onEditorAction {
                s.onNext(it)
                consumed
            }

            s.setDisposable(AndroidMainThreadDisposable({ item.setOnEditorActionListener(null) }))
        }


private class _TextView_EditorActionListener : TextView.OnEditorActionListener {

    private var editorAction: ((EditorActionEvent) -> Boolean)? = null

    override fun onEditorAction(p0: TextView, p1: Int, p2: KeyEvent): Boolean =
            editorAction?.invoke(EditorActionEvent(p0, p1, p2)) ?: false

    fun onEditorAction(listener: ((EditorActionEvent) -> Boolean)) {
        editorAction = listener
    }

}