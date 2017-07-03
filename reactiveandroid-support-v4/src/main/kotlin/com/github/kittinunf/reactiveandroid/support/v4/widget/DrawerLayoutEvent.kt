package com.github.kittinunf.reactiveandroid.support.v4.widget

import android.support.v4.widget.DrawerLayout
import android.view.View
import com.github.kittinunf.reactiveandroid.ExtensionFieldDelegate
import com.github.kittinunf.reactiveandroid.subscription.AndroidMainThreadSubscription
import io.reactivex.Observable

//================================================================================
// Events
//================================================================================

fun DrawerLayout.rx_drawerClosed(): Observable<View> {
    return Observable.create { subscriber ->
        _drawer.onDrawerClosed {
            if (it != null) subscriber.onNext(it)
        }

        subscriber.setDisposable(AndroidMainThreadSubscription {
            removeDrawerListener(_drawer)
        })
    }
}

fun DrawerLayout.rx_drawerStateChanged(): Observable<Int> {
    return Observable.create { subscriber ->
        _drawer.onDrawerStateChanged {
            subscriber.onNext(it)
        }

        subscriber.setDisposable(AndroidMainThreadSubscription {
            removeDrawerListener(_drawer)
        })
    }
}

data class DrawerSlideListener(val view: View?, val offset: Float)

fun DrawerLayout.rx_drawerSlide(): Observable<DrawerSlideListener> {
    return Observable.create { subscriber ->
        _drawer.onDrawerSlide { view, offset ->
            subscriber.onNext(DrawerSlideListener(view, offset))
        }

        subscriber.setDisposable(AndroidMainThreadSubscription {
            removeDrawerListener(_drawer)
        })
    }
}

fun DrawerLayout.rx_drawerOpened(): Observable<View> {
    return Observable.create { subscriber ->
        _drawer.onDrawerOpened {
            if (it != null) subscriber.onNext(it)
        }

        subscriber.setDisposable(AndroidMainThreadSubscription {
            removeDrawerListener(_drawer)
        })
    }
}

private val DrawerLayout._drawer: _DrawerLayout_DrawerListener
        by ExtensionFieldDelegate({ _DrawerLayout_DrawerListener() }, { addDrawerListener(it) })

internal class _DrawerLayout_DrawerListener : DrawerLayout.DrawerListener {

    private var onDrawerClosed: ((View?) -> Unit)? = null

    private var onDrawerStateChanged: ((Int) -> Unit)? = null

    private var onDrawerSlide: ((View?, Float) -> Unit)? = null

    private var onDrawerOpened: ((View?) -> Unit)? = null

    fun onDrawerClosed(listener: (View?) -> Unit) {
        onDrawerClosed = listener
    }

    override fun onDrawerClosed(drawerView: View?) {
        onDrawerClosed?.invoke(drawerView)
    }

    fun onDrawerStateChanged(listener: (Int) -> Unit) {
        onDrawerStateChanged = listener
    }

    override fun onDrawerStateChanged(newState: Int) {
        onDrawerStateChanged?.invoke(newState)
    }

    fun onDrawerSlide(listener: (View?, Float) -> Unit) {
        onDrawerSlide = listener
    }

    override fun onDrawerSlide(drawerView: View?, slideOffset: Float) {
        onDrawerSlide?.invoke(drawerView, slideOffset)
    }

    fun onDrawerOpened(listener: (View?) -> Unit) {
        onDrawerOpened = listener
    }

    override fun onDrawerOpened(drawerView: View?) {
        onDrawerOpened?.invoke(drawerView)
    }

}

 
