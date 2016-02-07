package com.github.kittinunf.reactiveandroid.widget

import android.view.View
import android.widget.AdapterView
import android.widget.AutoCompleteTextView
import com.github.kittinunf.reactiveandroid.subscription.AndroidMainThreadSubscription
import rx.Observable

//================================================================================
// Event 
//================================================================================

fun AutoCompleteTextView.rx_click(): Observable<View> {
    return Observable.create { subscriber ->
        setOnClickListener {
            subscriber.onNext(it)

        }
        
        subscriber.add(AndroidMainThreadSubscription {
            setOnClickListener(null)
        })
    }
}

fun AutoCompleteTextView.rx_dismiss(): Observable<Unit> {
    return Observable.create { subscriber ->
        setOnDismissListener { ->
            subscriber.onNext(Unit)

        }
        
        subscriber.add(AndroidMainThreadSubscription {
            setOnDismissListener(null)
        })
    }
}

fun AutoCompleteTextView.rx_itemClick(): Observable<ItemClickListener> {
    return Observable.create { subscriber ->
        setOnItemClickListener { parent, view, position, id ->
            subscriber.onNext(ItemClickListener(parent, view, position, id))
        }
        
        subscriber.add(AndroidMainThreadSubscription {
            setOnItemClickListener(null)
        })
    }
}

fun AutoCompleteTextView.rx_itemSelected(): Observable<ItemSelectedListener> {
    return Observable.create { subscriber ->
        _itemSelected.onItemSelected { adapterView, view, position, id ->
            subscriber.onNext(ItemSelectedListener(adapterView, view, position, id))
        }

        subscriber.add(AndroidMainThreadSubscription {
            setOnItemSelectedListener(null)
        })
    }
}

fun AutoCompleteTextView.rx_nothingSelected(): Observable<AdapterView<*>> {
    return Observable.create { subscriber ->
        _itemSelected.onNothingSelected {
            subscriber.onNext(it)
        }

        subscriber.add(AndroidMainThreadSubscription {
            setOnItemSelectedListener(null)
        })
    }
}

private val AutoCompleteTextView._itemSelected: _AdapterView_OnItemSelectedListener
    get() {
        val listener = _AdapterView_OnItemSelectedListener()
        onItemSelectedListener = listener
        return listener
    }




 
