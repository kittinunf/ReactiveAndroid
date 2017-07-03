package com.github.kittinunf.reactiveandroid.widget

import android.widget.RadioGroup
import com.github.kittinunf.reactiveandroid.subscription.AndroidMainThreadSubscription
import io.reactivex.Observable

//================================================================================
// Event 
//================================================================================

data class RadioGroupCheckedChangeListener(val radioGroup: RadioGroup, val checkedId: Int)

fun RadioGroup.rx_checkedChange(): Observable<RadioGroupCheckedChangeListener> {
    return Observable.create { subscriber ->
        setOnCheckedChangeListener { radioGroup, checkedId ->
            subscriber.onNext(RadioGroupCheckedChangeListener(radioGroup, checkedId))
        }
        
        subscriber.setDisposable(AndroidMainThreadSubscription {
            setOnCheckedChangeListener(null)
        })
    }
}

 
