package com.github.kittinunf.reactiveandroid.view

import android.view.MenuItem
import com.github.kittinunf.reactiveandroid.ExtensionFieldDelegate
import com.github.kittinunf.reactiveandroid.subscription.AndroidMainThreadSubscription
import io.reactivex.Observable

//================================================================================
// Events
//================================================================================

fun MenuItem.rx_actionExpand(expanded: Boolean): Observable<MenuItem> {
    return Observable.create { subscriber ->
        _actionExpand.onMenuItemActionExpand {
            if (it != null) subscriber.onNext(it)
            expanded
        }

        subscriber.setDisposable(AndroidMainThreadSubscription {
            setOnActionExpandListener(null)
        })
    }
}

fun MenuItem.rx_actionCollapse(collapsed: Boolean): Observable<MenuItem> {
    return Observable.create { subscriber ->
        _actionExpand.onMenuItemActionCollapse {
            if (it != null) subscriber.onNext(it)
            collapsed
        }

        subscriber.setDisposable(AndroidMainThreadSubscription {
            setOnActionExpandListener(null)
        })
    }
}

fun MenuItem.rx_menuItemClick(consumed: Boolean): Observable<MenuItem> {
    return Observable.create { subscriber ->
        setOnMenuItemClickListener {
            subscriber.onNext(it)
            consumed
        }

        subscriber.setDisposable(AndroidMainThreadSubscription {
            setOnMenuItemClickListener(null)
        })
    }
}

private val MenuItem._actionExpand: _MenuItem_ActionExpandListener
        by ExtensionFieldDelegate({ _MenuItem_ActionExpandListener() }, { setOnActionExpandListener(it) })

private class _MenuItem_ActionExpandListener : MenuItem.OnActionExpandListener {

    private var menuItemActionExpand: ((MenuItem?) -> Boolean)? = null

    private var menuItemActionCollapse: ((MenuItem?) -> Boolean)? = null

    override fun onMenuItemActionExpand(item: MenuItem?): Boolean = menuItemActionExpand?.invoke(item) ?: false

    fun onMenuItemActionExpand(listener: (MenuItem?) -> Boolean) {
        menuItemActionExpand = listener
    }

    override fun onMenuItemActionCollapse(item: MenuItem?): Boolean = menuItemActionCollapse?.invoke(item) ?: false

    fun onMenuItemActionCollapse(listener: (MenuItem?) -> Boolean) {
        menuItemActionCollapse = listener
    }

}
