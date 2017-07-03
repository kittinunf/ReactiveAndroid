package com.github.kittinunf.reactiveandroid.widget

import android.database.DataSetObserver
import android.widget.Adapter
import com.github.kittinunf.reactiveandroid.ExtensionFieldDelegate
import com.github.kittinunf.reactiveandroid.subscription.AndroidMainThreadSubscription
import io.reactivex.Observable

//================================================================================
// Events
//================================================================================

fun Adapter.rx_changed(): Observable<Unit> {
    return Observable.create { subscriber ->
        _dataSet.onChanged {
            subscriber.onNext(Unit)
        }

        val unregisterSubscription = AndroidMainThreadSubscription { unregisterDataSetObserver(_dataSet) }
        subscriber.setDisposable(unregisterSubscription)
    }
}

fun Adapter.rx_invalidated(): Observable<Unit> {
    return Observable.create { subscriber ->
        _dataSet.onInvalidated {
            subscriber.onNext(Unit)
        }

        subscriber.setDisposable(AndroidMainThreadSubscription {
            unregisterDataSetObserver(_dataSet)
        })
    }
}

private val Adapter._dataSet: _Adapter_DataSetObserver
        by ExtensionFieldDelegate({ _Adapter_DataSetObserver() }, { registerDataSetObserver(it) })

private class _Adapter_DataSetObserver : DataSetObserver() {

    var onChanged: (() -> Unit)? = null
    var onInvalidated: (() -> Unit)? = null

    fun onChanged(listener: () -> Unit) {
        onChanged = listener
    }

    override fun onChanged() {
        onChanged?.invoke()
    }

    fun onInvalidated(listener: () -> Unit) {
        onInvalidated = listener
    }

    override fun onInvalidated() {
        onInvalidated?.invoke()
    }

}
