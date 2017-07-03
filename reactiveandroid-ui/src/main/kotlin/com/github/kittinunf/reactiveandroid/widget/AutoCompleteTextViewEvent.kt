package com.github.kittinunf.reactiveandroid.widget

import android.widget.AdapterView
import android.widget.AutoCompleteTextView
import com.github.kittinunf.reactiveandroid.ExtensionFieldDelegate
import com.github.kittinunf.reactiveandroid.subscription.AndroidMainThreadSubscription
import io.reactivex.Observable

//================================================================================
// Event 
//================================================================================

fun AutoCompleteTextView.rx_dismiss(): Observable<Unit> {
    return Observable.create { subscriber ->
        setOnDismissListener { ->
            subscriber.onNext(Unit)

        }

        subscriber.setDisposable(AndroidMainThreadSubscription {
            setOnDismissListener(null)
        })
    }
}

fun AutoCompleteTextView.rx_itemClick(): Observable<ItemClickListener> {
    return Observable.create { subscriber ->
        setOnItemClickListener { parent, view, position, id ->
            subscriber.onNext(ItemClickListener(parent, view, position, id))
        }

        subscriber.setDisposable(AndroidMainThreadSubscription {
            onItemClickListener = null
        })
    }
}

fun AutoCompleteTextView.rx_itemSelected(): Observable<ItemSelectedListener> {
    return Observable.create { subscriber ->
        _itemSelected.onItemSelected { adapterView, view, position, id ->
            subscriber.onNext(ItemSelectedListener(adapterView, view, position, id))
        }

        subscriber.setDisposable(AndroidMainThreadSubscription {
            onItemSelectedListener = null
        })
    }
}

fun AutoCompleteTextView.rx_nothingSelected(): Observable<AdapterView<*>> {
    return Observable.create { subscriber ->
        _itemSelected.onNothingSelected {
            if (it != null) subscriber.onNext(it)
        }

        subscriber.setDisposable(AndroidMainThreadSubscription {
            onItemSelectedListener = null
        })
    }
}

private val AutoCompleteTextView._itemSelected: _AdapterView_OnItemSelectedListener
        by ExtensionFieldDelegate({ _AdapterView_OnItemSelectedListener() }, { onItemSelectedListener = it })
