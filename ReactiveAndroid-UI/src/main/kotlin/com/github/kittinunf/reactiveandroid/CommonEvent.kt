package com.github.kittinunf.reactiveandroid

import android.view.*
import android.widget.AdapterView
import android.widget.TextView

data class Padding(val start: Int, val top: Int, val end: Int, val bottom: Int)

data class DragListener(val view: View, val dragEvent: DragEvent)

data class KeyListener(val view: View, val keyCode: Int, val keyEvent: KeyEvent)

data class HoverListener(val view: View, val motionEvent: MotionEvent)

data class TouchListener(val view: View, val motionEvent: MotionEvent)

data class FocusChangeListener(val view: View, val hasFocus: Boolean)

data class LayoutChangeListener(val view: View, val newPadding: Padding, val oldPadding: Padding)

data class ScrollDirection(val x: Int, val y: Int)

data class ScrollChangeListener(val view: View, val direction: ScrollDirection, val oldDirection: ScrollDirection)

data class CreateContextMenuListener(val contextMenu: ContextMenu, val view: View, val menuInfo: ContextMenu.ContextMenuInfo)

data class EditorActionListener(val textView: TextView, val actionId: Int, val event: KeyEvent)

data class TextChangedListener(val text: CharSequence?, val start: Int, val before: Int, val count: Int)

data class ItemClickListener(val adapterView: AdapterView<*>, val view: View, val position: Int, val id: Long)

data class ItemSelectedListener(val adapterView: AdapterView<*>?, val view: View?, val position: Int, val id: Long)

data class ItemLongClickListener(val adapterView: AdapterView<*>, val view: View, val position: Int, val id: Long)
