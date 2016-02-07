package com.github.kittinunf.reactiveandroid.view

import android.animation.LayoutTransition
import android.view.ViewGroup
import android.view.animation.LayoutAnimationController
import com.github.kittinunf.reactiveandroid.MutableProperty
import com.github.kittinunf.reactiveandroid.createMainThreadMutableProperty

//================================================================================
// Properties
//================================================================================

private var _rx_layoutAnimation: MutableProperty<LayoutAnimationController>? = null

val ViewGroup.rx_layoutAnimation: MutableProperty<LayoutAnimationController>
    get() {
        val getter = { layoutAnimation }
        val setter: (LayoutAnimationController) -> Unit = { layoutAnimation = it }
        
        if (_rx_layoutAnimation == null) {
            _rx_layoutAnimation = createMainThreadMutableProperty(getter, setter)
        } else {
            _rx_layoutAnimation!!.value = getter()
        }
        return _rx_layoutAnimation!!
    }

private var _rx_clipChildren: MutableProperty<Boolean>? = null

val ViewGroup.rx_clipChildren: MutableProperty<Boolean>
    get() {
        val getter = { clipChildren }
        val setter: (Boolean) -> Unit = { clipChildren = it }
        
        if (_rx_clipChildren == null) {
            _rx_clipChildren = createMainThreadMutableProperty(getter, setter)
        } else {
            _rx_clipChildren!!.value = getter()
        }
        return _rx_clipChildren!!
    }

private var _rx_clipToPadding: MutableProperty<Boolean>? = null

val ViewGroup.rx_clipToPadding: MutableProperty<Boolean>
    get() {
        val getter = { clipToPadding }
        val setter: (Boolean) -> Unit = { clipToPadding = it }

        if (_rx_clipToPadding == null) {
            _rx_clipToPadding = createMainThreadMutableProperty(getter, setter)
        } else {
            _rx_clipToPadding!!.value = getter()
        }
        return _rx_clipToPadding!!
    }

private var _rx_layoutMode: MutableProperty<Int>? = null

val ViewGroup.rx_layoutMode: MutableProperty<Int>
    get() {
        val getter = { layoutMode }
        val setter: (Int) -> Unit = { layoutMode = it }
        
        if (_rx_layoutMode == null) {
            _rx_layoutMode = createMainThreadMutableProperty(getter, setter)
        } else {
            _rx_layoutMode!!.value = getter()
        }
        return _rx_layoutMode!!
    }

private var _rx_layoutTransition: MutableProperty<LayoutTransition>? = null

val ViewGroup.rx_layoutTransition: MutableProperty<LayoutTransition>
    get() {
        val getter = { layoutTransition }
        val setter: (LayoutTransition) -> Unit = { layoutTransition = it }
        
        if (_rx_layoutTransition == null) {
            _rx_layoutTransition = createMainThreadMutableProperty(getter, setter)
        } else {
            _rx_layoutTransition!!.value = getter()
        }
        return _rx_layoutTransition!!
    }

