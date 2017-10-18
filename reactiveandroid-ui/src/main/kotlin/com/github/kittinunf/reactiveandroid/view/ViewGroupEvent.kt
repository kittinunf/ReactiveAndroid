package com.github.kittinunf.reactiveandroid.view

import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import com.github.kittinunf.reactiveandroid.ExtensionFieldDelegate
import com.github.kittinunf.reactiveandroid.subscription.AndroidMainThreadSubscription
import io.reactivex.Observable

//================================================================================
// Events
//================================================================================

fun ViewGroup.rx_animationRepeat(): Observable<Animation> {
    return Observable.create { subscriber ->
        _layout_animation.onAnimationRepeat {
            if (it != null) subscriber.onNext(it)
        }

        subscriber.setDisposable(AndroidMainThreadSubscription {
            layoutAnimationListener = null
        })
    }
}

fun ViewGroup.rx_animationEnd(): Observable<Animation> {
    return Observable.create { subscriber ->
        _layout_animation.onAnimationEnd {
            if (it != null) subscriber.onNext(it)
        }

        subscriber.setDisposable(AndroidMainThreadSubscription {
            layoutAnimationListener = null
        })
    }
}

fun ViewGroup.rx_animationStart(): Observable<Animation> {
    return Observable.create { subscriber ->
        _layout_animation.onAnimationStart {
            if (it != null) subscriber.onNext(it)
        }

        subscriber.setDisposable(AndroidMainThreadSubscription {
            layoutAnimationListener = null
        })
    }
}

data class HierarchyChangeListener(val parent: View?, val child: View?)

fun ViewGroup.rx_childViewRemoved(): Observable<HierarchyChangeListener> {
    return Observable.create { subscriber ->
        _hierarchyChange.onChildViewRemoved { parent, child ->
            subscriber.onNext(HierarchyChangeListener(parent, child))
        }

        subscriber.setDisposable(AndroidMainThreadSubscription {
            setOnHierarchyChangeListener(null)
        })
    }
}

fun ViewGroup.rx_childViewAdded(): Observable<HierarchyChangeListener> {
    return Observable.create { subscriber ->
        _hierarchyChange.onChildViewAdded { parent, child ->
            subscriber.onNext(HierarchyChangeListener(parent, child))
        }

        subscriber.setDisposable(AndroidMainThreadSubscription {
            setOnHierarchyChangeListener(null)
        })
    }
}

private val ViewGroup._hierarchyChange: _ViewGroup_OnHierarchyChangeListener
        by ExtensionFieldDelegate({ _ViewGroup_OnHierarchyChangeListener() }, { setOnHierarchyChangeListener(it) })

internal class _ViewGroup_OnHierarchyChangeListener : ViewGroup.OnHierarchyChangeListener {

    private var onChildViewRemoved: ((View?, View?) -> Unit)? = null

    private var onChildViewAdded: ((View?, View?) -> Unit)? = null

    fun onChildViewRemoved(listener: (View?, View?) -> Unit) {
        onChildViewRemoved = listener
    }

    fun onChildViewAdded(listener: (View?, View?) -> Unit) {
        onChildViewAdded = listener
    }

    override fun onChildViewRemoved(parent: View?, child: View?) {
        onChildViewRemoved?.invoke(parent, child)
    }

    override fun onChildViewAdded(parent: View?, child: View?) {
        onChildViewAdded?.invoke(parent, child)
    }

}

private val ViewGroup._layout_animation: _ViewGroup_AnimationListener
        by ExtensionFieldDelegate({ _ViewGroup_AnimationListener() }, { layoutAnimationListener = it })

internal class _ViewGroup_AnimationListener : Animation.AnimationListener {

    private var onAnimationRepeat: ((Animation?) -> Unit)? = null

    private var onAnimationEnd: ((Animation?) -> Unit)? = null

    private var onAnimationStart: ((Animation?) -> Unit)? = null

    fun onAnimationRepeat(listener: (Animation?) -> Unit) {
        onAnimationRepeat = listener
    }

    fun onAnimationEnd(listener: (Animation?) -> Unit) {
        onAnimationEnd = listener
    }

    fun onAnimationStart(listener: (Animation?) -> Unit) {
        onAnimationStart = listener
    }

    override fun onAnimationRepeat(animation: Animation?) {
        onAnimationRepeat?.invoke(animation)
    }

    override fun onAnimationEnd(animation: Animation?) {
        onAnimationEnd?.invoke(animation)
    }

    override fun onAnimationStart(animation: Animation?) {
        onAnimationStart?.invoke(animation)
    }

}
