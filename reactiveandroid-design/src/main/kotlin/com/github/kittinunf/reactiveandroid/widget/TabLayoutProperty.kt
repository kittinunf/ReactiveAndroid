package com.github.kittinunf.reactiveandroid.widget

import android.content.res.ColorStateList
import android.support.design.widget.TabLayout
import com.github.kittinunf.reactiveandroid.MutableProperty
import com.github.kittinunf.reactiveandroid.createMainThreadMutableProperty

//================================================================================
// Properties
//================================================================================

val TabLayout.rx_select: MutableProperty<Int>
    get() {
        val getter = { selectedTabPosition }
        val setter: (Int) -> Unit = { getTabAt(it)?.select() }

        return createMainThreadMutableProperty(getter, setter)
    }

val TabLayout.rx_tabGravity: MutableProperty<Int>
    get() {
        val getter = { tabGravity }
        val setter: (Int) -> Unit = { tabGravity = it }

        return createMainThreadMutableProperty(getter, setter)
    }

val TabLayout.rx_tabMode: MutableProperty<Int>
    get() {
        val getter = { tabMode }
        val setter: (Int) -> Unit = { tabMode = it }

        return createMainThreadMutableProperty(getter, setter)
    }

val TabLayout.rx_tabTextColors: MutableProperty<ColorStateList?>
    get() {
        val getter = { tabTextColors }
        val setter: (ColorStateList?) -> Unit = { tabTextColors = it }

        return createMainThreadMutableProperty(getter, setter)
    }




 
