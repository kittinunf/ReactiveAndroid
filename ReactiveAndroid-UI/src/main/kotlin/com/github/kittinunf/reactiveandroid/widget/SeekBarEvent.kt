package com.github.kittinunf.reactiveandroid.widget

import android.widget.SeekBar
import com.github.kittinunf.reactiveandroid.subscription.AndroidMainThreadSubscription
import rx.Observable

//================================================================================
// Events
//================================================================================

data class SeekBarProgressChangeListener(val seekBar: SeekBar?, val progress: Int, val fromUser: Boolean)

fun SeekBar.rx_progressChanged(): Observable<SeekBarProgressChangeListener> {
    return Observable.create { subscriber ->
        _seekBarChange.onProgressChanged { seekBar, progress, fromUser ->
            subscriber.onNext(SeekBarProgressChangeListener(seekBar, progress, fromUser))
        }

        subscriber.add(AndroidMainThreadSubscription {
            setOnSeekBarChangeListener(null)
        })
    }
}

fun SeekBar.rx_startTrackingTouch(): Observable<SeekBar> {
    return Observable.create { subscriber ->
        _seekBarChange.onStartTrackingTouch {
            subscriber.onNext(it)
        }

        subscriber.add(AndroidMainThreadSubscription {
            setOnSeekBarChangeListener(null)
        })
    }
}

fun SeekBar.rx_stopTrackingTouch(): Observable<SeekBar> {
    return Observable.create { subscriber ->
        _seekBarChange.onStopTrackingTouch {
            subscriber.onNext(it)
        }

        subscriber.add(AndroidMainThreadSubscription {
            setOnSeekBarChangeListener(null)
        })
    }
}

val SeekBar._seekBarChange: _SeekBar_OnSeekBarChangeListener
    get() {
        val listener = _SeekBar_OnSeekBarChangeListener()
        setOnSeekBarChangeListener(listener)
        return listener
    }

class _SeekBar_OnSeekBarChangeListener : SeekBar.OnSeekBarChangeListener {

    var onProgressChanged: ((SeekBar?, Int, Boolean) -> Unit)? = null

    var onStartTrackingTouch: ((SeekBar?) -> Unit)? = null

    var onStopTrackingTouch: ((SeekBar?) -> Unit)? = null

    fun onProgressChanged(listener: (SeekBar?, Int, Boolean) -> Unit) {
        onProgressChanged = listener
    }

    override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
        onProgressChanged?.invoke(seekBar, progress, fromUser)
    }

    fun onStartTrackingTouch(listener: (SeekBar?) -> Unit) {
        onStartTrackingTouch = listener
    }

    override fun onStartTrackingTouch(seekBar: SeekBar?) {
        onStartTrackingTouch?.invoke(seekBar)
    }

    fun onStopTrackingTouch(listener: (SeekBar?) -> Unit) {
        onStopTrackingTouch = listener
    }

    override fun onStopTrackingTouch(seekBar: SeekBar?) {
        onStopTrackingTouch?.invoke(seekBar)
    }

}



 
