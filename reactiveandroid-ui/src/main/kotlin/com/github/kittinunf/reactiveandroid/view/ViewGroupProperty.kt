package com.github.kittinunf.reactiveandroid.view

import android.animation.LayoutTransition
import android.view.ViewGroup
import android.view.animation.LayoutAnimationController
import com.github.kittinunf.reactiveandroid.MutableProperty
import com.github.kittinunf.reactiveandroid.createMainThreadMutableProperty

//================================================================================
// Properties
//================================================================================


val ViewGroup.rx_layoutAnimation: MutableProperty<LayoutAnimationController>
    get() {
        val getter = { layoutAnimation }
        val setter: (LayoutAnimationController) -> Unit = { layoutAnimation = it }

        return createMainThreadMutableProperty(getter, setter)
    }

val ViewGroup.rx_clipChildren: MutableProperty<Boolean>
    get() {
        val getter = { clipChildren }
        val setter: (Boolean) -> Unit = { clipChildren = it }

        return createMainThreadMutableProperty(getter, setter)
    }

val ViewGroup.rx_clipToPadding: MutableProperty<Boolean>
    get() {
        val getter = { clipToPadding }
        val setter: (Boolean) -> Unit = { clipToPadding = it }

        return createMainThreadMutableProperty(getter, setter)
    }

val ViewGroup.rx_layoutMode: MutableProperty<Int>
    get() {
        val getter = { layoutMode }
        val setter: (Int) -> Unit = { layoutMode = it }

        return createMainThreadMutableProperty(getter, setter)
    }

val ViewGroup.rx_layoutTransition: MutableProperty<LayoutTransition>
    get() {
        val getter = { layoutTransition }
        val setter: (LayoutTransition) -> Unit = { layoutTransition = it }

        return createMainThreadMutableProperty(getter, setter)
    }
