package com.github.kittinunf.reactiveandroid.widget

import android.widget.CompoundButton
import com.github.kittinunf.reactiveandroid.subscription.AndroidMainThreadSubscription
import io.reactivex.Observable

//================================================================================
// Event
//================================================================================

data class CompoundButtonCheckedChangeListener(val compoundButton: CompoundButton, val isChecked: Boolean)

fun CompoundButton.rx_checkedChange(): Observable<CompoundButtonCheckedChangeListener> {
    return Observable.create { subscriber ->
        setOnCheckedChangeListener { compoundButton, isChecked ->
            subscriber.onNext(CompoundButtonCheckedChangeListener(compoundButton, isChecked))
        }
        
        subscriber.setDisposable(AndroidMainThreadSubscription {
            setOnCheckedChangeListener(null)
        })
    }
}

 
