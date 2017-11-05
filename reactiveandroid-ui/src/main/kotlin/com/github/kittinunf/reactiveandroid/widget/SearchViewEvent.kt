package com.github.kittinunf.reactiveandroid.widget

import android.view.View
import android.widget.SearchView
import com.github.kittinunf.reactiveandroid.ExtensionFieldDelegate
import com.github.kittinunf.reactiveandroid.reactive.view.FocusChangeListener
import com.github.kittinunf.reactiveandroid.subscription.AndroidMainThreadSubscription
import io.reactivex.Observable

//================================================================================
// Events 
//================================================================================

fun SearchView.rx_close(overriden: Boolean): Observable<Unit> {
    return Observable.create { subscriber ->
        setOnCloseListener {
            subscriber.onNext(Unit)
            overriden
        }

        subscriber.setDisposable(AndroidMainThreadSubscription {
            setOnCloseListener(null)
        })
    }
}

fun SearchView.rx_queryTextFocusChange(): Observable<FocusChangeListener> {
    return Observable.create { subscriber ->
        setOnQueryTextFocusChangeListener { view, hasFocus ->
            subscriber.onNext(FocusChangeListener(view, hasFocus))
        }

        subscriber.setDisposable(AndroidMainThreadSubscription {
            setOnQueryTextFocusChangeListener(null)
        })
    }
}

fun SearchView.rx_queryTextChange(consumed: Boolean): Observable<String> {
    return Observable.create { subscriber ->
        _queryText.onQueryTextChange {
            if (it != null) subscriber.onNext(it)
            consumed
        }

        subscriber.setDisposable(AndroidMainThreadSubscription {
            setOnQueryTextListener(null)
        })
    }
}

fun SearchView.rx_queryTextSubmit(consumed: Boolean): Observable<String> {
    return Observable.create { subscriber ->
        _queryText.onQueryTextSubmit {
            if (it != null) subscriber.onNext(it)
            consumed
        }

        subscriber.setDisposable(AndroidMainThreadSubscription {
            setOnQueryTextListener(null)
        })
    }
}

fun SearchView.rx_searchClick(): Observable<View> {
    return Observable.create { subscriber ->
        setOnSearchClickListener {
            subscriber.onNext(it)
        }

        subscriber.setDisposable(AndroidMainThreadSubscription {
            setOnSearchClickListener(null)
        })
    }
}

fun SearchView.rx_suggestionSelect(consumed: Boolean): Observable<Int> {
    return Observable.create { subscriber ->
        _suggestion.onSuggestionSelect {
            subscriber.onNext(it)
            consumed
        }

        subscriber.setDisposable(AndroidMainThreadSubscription {
            setOnSuggestionListener(null)
        })
    }
}

fun SearchView.rx_suggestionClick(consumed: Boolean): Observable<Int> {
    return Observable.create { subscriber ->
        _suggestion.onSuggestionClick {
            subscriber.onNext(it)
            consumed
        }

        subscriber.setDisposable(AndroidMainThreadSubscription {
            setOnSuggestionListener(null)
        })
    }
}

private val SearchView._suggestion: _SearchView_OnSuggestionListener
        by ExtensionFieldDelegate({ _SearchView_OnSuggestionListener() }, { setOnSuggestionListener(it) })

internal class _SearchView_OnSuggestionListener : SearchView.OnSuggestionListener {

    private var onSuggestionSelect: ((Int) -> Boolean)? = null

    private var onSuggestionClick: ((Int) -> Boolean)? = null

    fun onSuggestionSelect(listener: (Int) -> Boolean) {
        onSuggestionSelect = listener
    }

    override fun onSuggestionSelect(position: Int): Boolean {
        return onSuggestionSelect?.invoke(position) ?: false
    }

    fun onSuggestionClick(listener: (Int) -> Boolean) {
        onSuggestionClick = listener
    }

    override fun onSuggestionClick(position: Int): Boolean {
        return onSuggestionClick?.invoke(position) ?: false
    }

}

private val SearchView._queryText: _SearchView_OnQueryTextListener
        by ExtensionFieldDelegate({ _SearchView_OnQueryTextListener() }, { setOnQueryTextListener(it) })

private class _SearchView_OnQueryTextListener : SearchView.OnQueryTextListener {

    private var onQueryTextSubmit: ((String?) -> Boolean)? = null

    private var onQueryTextChange: ((String?) -> Boolean)? = null

    fun onQueryTextSubmit(listener: (String?) -> Boolean) {
        onQueryTextSubmit = listener
    }

    override fun onQueryTextSubmit(query: String?): Boolean {
        return onQueryTextSubmit?.invoke(query) ?: false
    }

    fun onQueryTextChange(listener: (String?) -> Boolean) {
        onQueryTextChange = listener
    }

    override fun onQueryTextChange(newText: String?): Boolean {
        return onQueryTextChange?.invoke(newText) ?: false
    }

}
