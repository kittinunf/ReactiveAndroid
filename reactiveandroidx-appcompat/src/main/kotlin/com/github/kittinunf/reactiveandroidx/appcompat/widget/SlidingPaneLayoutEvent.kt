package com.github.kittinunf.reactiveandroidx.appcompat.widget

import android.view.View
import androidx.slidingpanelayout.widget.SlidingPaneLayout
import com.github.kittinunf.reactiveandroid.ExtensionFieldDelegate
import com.github.kittinunf.reactiveandroid.subscription.AndroidMainThreadSubscription
import io.reactivex.Observable

//================================================================================
// Events 
//================================================================================

data class PanelSlideListener(val panel: View?, val slideOffset: Float)

fun SlidingPaneLayout.rx_panelSlide(): Observable<PanelSlideListener> {
    return Observable.create { subscriber ->
        _panelSlide.onPanelSlide { view, offset ->
            subscriber.onNext(PanelSlideListener(view, offset))
        }

        subscriber.setDisposable(AndroidMainThreadSubscription {
            setPanelSlideListener(null)
        })
    }
}

fun SlidingPaneLayout.rx_panelOpened(): Observable<View> {
    return Observable.create { subscriber ->
        _panelSlide.onPanelOpened {
            if (it != null) subscriber.onNext(it)
        }

        subscriber.setDisposable(AndroidMainThreadSubscription {
            setPanelSlideListener(null)
        })
    }
}

fun SlidingPaneLayout.rx_panelClosed(): Observable<View> {
    return Observable.create { subscriber ->
        _panelSlide.onPanelClosed {
            if (it != null) subscriber.onNext(it)
        }

        subscriber.setDisposable(AndroidMainThreadSubscription {
            setPanelSlideListener(null)
        })
    }
}

private val SlidingPaneLayout._panelSlide: _SlidingPaneLayout_PanelSlideListener
        by ExtensionFieldDelegate({ _SlidingPaneLayout_PanelSlideListener() }, { setPanelSlideListener(it) })

internal class _SlidingPaneLayout_PanelSlideListener : SlidingPaneLayout.PanelSlideListener {

    private var onPanelSlide: ((View?, Float) -> Unit)? = null

    private var onPanelClosed: ((View?) -> Unit)? = null

    private var onPanelOpened: ((View?) -> Unit)? = null

    fun onPanelSlide(listener: (View?, Float) -> Unit) {
        onPanelSlide = listener
    }

    override fun onPanelSlide(panel: View, slideOffset: Float) {
        onPanelSlide?.invoke(panel, slideOffset)
    }

    fun onPanelClosed(listener: (View?) -> Unit) {
        onPanelClosed = listener
    }

    override fun onPanelClosed(panel: View) {
        onPanelClosed?.invoke(panel)
    }

    fun onPanelOpened(listener: (View?) -> Unit) {
        onPanelOpened = listener
    }

    override fun onPanelOpened(panel: View) {
        onPanelOpened?.invoke(panel)
    }

}
 
