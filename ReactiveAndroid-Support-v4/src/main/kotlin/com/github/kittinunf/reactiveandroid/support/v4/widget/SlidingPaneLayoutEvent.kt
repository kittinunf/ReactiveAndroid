package com.github.kittinunf.reactiveandroid.support.v4.widget

import android.support.v4.widget.SlidingPaneLayout
import android.view.View
import com.github.kittinunf.reactiveandroid.subscription.AndroidMainThreadSubscription
import rx.Observable

//================================================================================
// Events 
//================================================================================

data class PanelSlideListener(val panel: View?, val slideOffset: Float)

fun SlidingPaneLayout.rx_panelSlide(): Observable<PanelSlideListener> {
    return Observable.create { subscriber ->
        _panelSlide.onPanelSlide { view, offset ->
            subscriber.onNext(PanelSlideListener(view, offset))
        }
        
        subscriber.add(AndroidMainThreadSubscription {
            setPanelSlideListener(null)
        })
    }
}

fun SlidingPaneLayout.rx_panelOpened(): Observable<View> {
    return Observable.create { subscriber ->
        _panelSlide.onPanelOpened {
            subscriber.onNext(it)
        }

        subscriber.add(AndroidMainThreadSubscription {
            setPanelSlideListener(null)
        })
    }
}

fun SlidingPaneLayout.rx_panelClosed(): Observable<View> {
    return Observable.create { subscriber ->
        _panelSlide.onPanelClosed {
            subscriber.onNext(it)
        }

        subscriber.add(AndroidMainThreadSubscription {
            setPanelSlideListener(null)
        })
    }
}

private val SlidingPaneLayout._panelSlide: _SlidingPaneLayout_PanelSlideListener
    get() {
        val listener = _SlidingPaneLayout_PanelSlideListener()
        setPanelSlideListener(listener)
        return listener
    }

class _SlidingPaneLayout_PanelSlideListener : SlidingPaneLayout.PanelSlideListener {

    private var onPanelSlide: ((View?, Float) -> Unit)? = null

    private var onPanelClosed: ((View?) -> Unit)? = null

    private var onPanelOpened: ((View?) -> Unit)? = null

    fun onPanelSlide(listener: (View?, Float) -> Unit) {
        onPanelSlide = listener
    }

    override fun onPanelSlide(panel: View?, slideOffset: Float) {
        onPanelSlide?.invoke(panel, slideOffset)
    }

    fun onPanelClosed(listener: (View?) -> Unit) {
        onPanelClosed = listener
    }

    override fun onPanelClosed(panel: View?) {
        onPanelClosed?.invoke(panel)
    }

    fun onPanelOpened(listener: (View?) -> Unit) {
        onPanelOpened = listener
    }

    override fun onPanelOpened(panel: View?) {
        onPanelOpened?.invoke(panel)
    }

}
 
