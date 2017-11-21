package com.github.kittinunf.reactiveandroid.reactive.widget

import android.content.res.ColorStateList
import android.graphics.Typeface
import android.text.Editable
import android.text.TextWatcher
import android.view.KeyEvent
import android.widget.TextView
import com.github.kittinunf.reactiveandroid.FieldDelegate
import com.github.kittinunf.reactiveandroid.internal.AndroidMainThreadDisposable
import com.github.kittinunf.reactiveandroid.reactive.AndroidBindingConsumer
import com.github.kittinunf.reactiveandroid.reactive.Reactive
import com.github.kittinunf.reactiveandroid.reactive.ofType
import io.reactivex.Observable
import io.reactivex.functions.Consumer

val TextView.rx: Reactive<TextView> by FieldDelegate({ Reactive(it) })

//Properties

val Reactive<TextView>.text: Consumer<CharSequence>
    get() = AndroidBindingConsumer(item) { item, text ->
        item.text = text
    }

val Reactive<TextView>.error: Consumer<CharSequence>
    get() = AndroidBindingConsumer(item) { item, error ->
        item.error = error
    }

val Reactive<TextView>.hint: Consumer<CharSequence>
    get() = AndroidBindingConsumer(item) { item, hint ->
        item.hint = hint
    }

val Reactive<TextView>.textColor: Consumer<Int>
    get() = AndroidBindingConsumer(item) { item, color ->
        item.setTextColor(color)
    }

val Reactive<TextView>.textColors: Consumer<ColorStateList>
    get() = AndroidBindingConsumer(item) { item, colors ->
        item.setTextColor(colors)
    }

val Reactive<TextView>.typeface: Consumer<Typeface>
    get() = AndroidBindingConsumer(item) { item, typeface ->
        item.typeface = typeface
    }

//Listeners

sealed class TextWatcherEvent {
    data class AfterTextChanged(val s: CharSequence?) : TextWatcherEvent()
    data class OnTextChanged(val s: CharSequence?, val start: Int, val before: Int, val count: Int) : TextWatcherEvent()
    data class BeforeTextChanged(val s: CharSequence?, val start: Int, val count: Int, val after: Int) : TextWatcherEvent()
}

fun Reactive<TextView>.text() = textWatcher().ofType<TextWatcherEvent.OnTextChanged>().map { it.s ?: "" }

fun Reactive<TextView>.textChanged() = textWatcher().ofType<TextWatcherEvent.OnTextChanged>()

fun Reactive<TextView>.beforeTextChanged() = textWatcher().ofType<TextWatcherEvent.BeforeTextChanged>()

fun Reactive<TextView>.afterTextChanged() = textWatcher().ofType<TextWatcherEvent.AfterTextChanged>()

private fun Reactive<TextView>.textWatcher(): Observable<TextWatcherEvent> =
        Observable.create<TextWatcherEvent> { emitter ->
            val listener = object : TextWatcher {
                override fun afterTextChanged(s: Editable?) {
                    if (!emitter.isDisposed) {
                        emitter.onNext(TextWatcherEvent.AfterTextChanged(s))
                    }
                }

                override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                    if (!emitter.isDisposed) {
                        emitter.onNext(TextWatcherEvent.BeforeTextChanged(s, start, count, after))
                    }
                }

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                    if (!emitter.isDisposed) {
                        emitter.onNext(TextWatcherEvent.OnTextChanged(s, start, before, count))
                    }
                }
            }

            item.addTextChangedListener(listener)

            emitter.setDisposable(AndroidMainThreadDisposable { item.removeTextChangedListener(listener) })
        }.startWith(TextWatcherEvent.OnTextChanged(item.text, 0, 0, 0))

data class EditorActionEvent(val textView: TextView, val actionId: Int, val event: KeyEvent?)

fun Reactive<TextView>.onEditorAction(consumed: Boolean = true): Observable<EditorActionEvent> =
        Observable.create { emitter ->
            item.setOnEditorActionListener { textView, actionId, keyEvent ->
                if (!emitter.isDisposed) {
                    emitter.onNext(EditorActionEvent(textView, actionId, keyEvent))
                }

                consumed
            }

            emitter.setDisposable(AndroidMainThreadDisposable { item.setOnEditorActionListener(null) })
        }