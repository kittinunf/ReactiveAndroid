package com.github.kittinunf.reactiveandroid.widget

import android.view.View
import android.widget.AdapterView
import com.github.kittinunf.reactiveandroid.ItemClickListener
import com.github.kittinunf.reactiveandroid.ItemLongClickListener
import com.github.kittinunf.reactiveandroid.ItemSelectedListener
import com.github.kittinunf.reactiveandroid.subscription.AndroidMainThreadSubscription
import rx.Observable

//================================================================================
// Events
//================================================================================

fun AdapterView<*>.rx_itemClick(): Observable<ItemClickListener> {
    return Observable.create { subscriber ->
        setOnItemClickListener { adapterView, view, position, id ->
            subscriber.onNext(ItemClickListener(adapterView, view, position, id))
        }

        subscriber.add(AndroidMainThreadSubscription {
            setOnItemClickListener(null)
        })
    }
}

fun AdapterView<*>.rx_itemLongClick(consumed: Boolean): Observable<ItemLongClickListener> {
    return Observable.create { subscriber ->
        setOnItemLongClickListener { adapterView, view, position, id ->
            subscriber.onNext(ItemLongClickListener(adapterView, view, position, id))
            consumed
        }

        subscriber.add(AndroidMainThreadSubscription {
           setOnItemLongClickListener(null)
        })
    }
}


fun AdapterView<*>.rx_itemSelected(): Observable<ItemSelectedListener> {
    return Observable.create { subscriber ->
        _itemSelected.onItemSelected { adapterView, view, position, id ->
            subscriber.onNext(ItemSelectedListener(adapterView, view, position, id))
        }

        subscriber.add(AndroidMainThreadSubscription {
            onItemSelectedListener = null
        })
    }
}

fun AdapterView<*>.rx_nothingSelected(): Observable<AdapterView<*>> {
    return Observable.create { subscriber ->
        _itemSelected.onNothingSelected {
            subscriber.onNext(it)
        }

        subscriber.add(AndroidMainThreadSubscription {
            onItemSelectedListener = null
        })
    }
}


private val AdapterView<*>._itemSelected: _AdapterView_OnItemSelectedListener
    get() {
        val listener = _AdapterView_OnItemSelectedListener()
        onItemSelectedListener = listener
        return listener
    }

private class _AdapterView_OnItemSelectedListener : AdapterView.OnItemSelectedListener {

    private var onNothingSelected: ((AdapterView<*>?) -> Unit)? = null

    private var onItemSelected: ((AdapterView<*>?, View?, Int, Long) -> Unit)? = null

    override fun onNothingSelected(parent: AdapterView<*>?) {
        onNothingSelected?.invoke(parent)
    }

    //proxy method
    fun onNothingSelected(listener: (AdapterView<*>?) -> Unit) {
        onNothingSelected = listener
    }

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        onItemSelected?.invoke(parent, view, position, id)
    }

    //proxy method
    fun onItemSelected(listener: (AdapterView<*>?, View?, Int, Long) -> Unit) {
        onItemSelected = listener
    }

}



