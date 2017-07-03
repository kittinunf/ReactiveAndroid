package com.github.kittinunf.reactiveandroid.view

import android.content.Context
import android.content.Intent
import android.graphics.drawable.Drawable
import android.view.ActionProvider
import android.view.ContextMenu
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.SubMenu
import android.view.View

class TestMenuItem(val context: Context) : MenuItem {

    var menuItemClickListener: MenuItem.OnMenuItemClickListener? = null

    var actionExpandListener: MenuItem.OnActionExpandListener? = null

    var actionViewExpanded = false

    var menuActionView: View? = null

    var menuIcon: Drawable? = null

    var menuCheckable = false

    var menuIsChecked = false

    var menuEnabled = false

    var menuVisible = false

    var menuTitle: CharSequence = ""

    fun performClick() {
        menuItemClickListener?.onMenuItemClick(this)
    }

    override fun setOnMenuItemClickListener(listener: MenuItem.OnMenuItemClickListener?): MenuItem {
        menuItemClickListener = listener
        return this
    }

    override fun expandActionView(): Boolean {
        if (actionViewExpanded) return false

        val shouldExpand = actionExpandListener?.onMenuItemActionExpand(this) ?: false

        if (!shouldExpand) return false

        actionViewExpanded = true
        return true
    }

    override fun collapseActionView(): Boolean {
        if (!actionViewExpanded) return false

        val shouldCollapse = actionExpandListener?.onMenuItemActionCollapse(this) ?: false

        if (!shouldCollapse) return false

        actionViewExpanded = false
        return true
    }

    override fun isActionViewExpanded(): Boolean {
        return actionViewExpanded
    }

    override fun setOnActionExpandListener(listener: MenuItem.OnActionExpandListener?): MenuItem {
        actionExpandListener = listener
        return this
    }

    override fun getActionView(): View? {
        return menuActionView
    }

    override fun setActionView(view: View?): MenuItem {
        menuActionView = view
        return this
    }

    override fun setActionView(resId: Int): MenuItem {
        menuActionView = LayoutInflater.from(context).inflate(resId, null)
        return this
    }

    override fun setIcon(icon: Drawable?): MenuItem {
        menuIcon = icon
        return this
    }

    override fun setIcon(iconRes: Int): MenuItem {
        menuIcon = context.getDrawable(iconRes)
        return this
    }

    override fun getIcon(): Drawable? {
        return menuIcon
    }

    override fun isCheckable(): Boolean {
        return menuCheckable
    }

    override fun setCheckable(checkable: Boolean): MenuItem {
        menuCheckable = checkable
        return this
    }

    override fun isChecked(): Boolean {
        return menuIsChecked
    }

    override fun setChecked(checked: Boolean): MenuItem {
        menuIsChecked = checked
        return this
    }

    override fun setEnabled(enabled: Boolean): MenuItem {
        menuEnabled = enabled
        return this
    }

    override fun isEnabled(): Boolean {
        return menuEnabled
    }

    override fun setVisible(visible: Boolean): MenuItem {
        menuVisible = visible
        return this
    }

    override fun isVisible(): Boolean {
        return menuVisible
    }

    override fun setTitle(title: CharSequence?): MenuItem {
        title?.let {
            menuTitle = it
        }
        return this
    }

    override fun setTitle(title: Int): MenuItem {
        menuTitle = context.getString(title)
        return this
    }

    override fun getTitle(): CharSequence {
        return menuTitle
    }

    override fun setAlphabeticShortcut(alphaChar: Char): MenuItem {
        throw UnsupportedOperationException("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getOrder(): Int {
        throw UnsupportedOperationException("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun hasSubMenu(): Boolean {
        throw UnsupportedOperationException("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getMenuInfo(): ContextMenu.ContextMenuInfo {
        throw UnsupportedOperationException("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getItemId(): Int {
        throw UnsupportedOperationException("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getAlphabeticShortcut(): Char {
        throw UnsupportedOperationException("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun setIntent(intent: Intent?): MenuItem {
        throw UnsupportedOperationException("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun setShortcut(numericChar: Char, alphaChar: Char): MenuItem {
        throw UnsupportedOperationException("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun setShowAsActionFlags(actionEnum: Int): MenuItem {
        throw UnsupportedOperationException("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getIntent(): Intent {
        throw UnsupportedOperationException("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getActionProvider(): ActionProvider {
        throw UnsupportedOperationException("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun setShowAsAction(actionEnum: Int) {
        throw UnsupportedOperationException("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getGroupId(): Int {
        throw UnsupportedOperationException("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun setActionProvider(actionProvider: ActionProvider?): MenuItem {
        throw UnsupportedOperationException("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun setTitleCondensed(title: CharSequence?): MenuItem {
        throw UnsupportedOperationException("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getSubMenu(): SubMenu {
        throw UnsupportedOperationException("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getNumericShortcut(): Char {
        throw UnsupportedOperationException("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getTitleCondensed(): CharSequence {
        throw UnsupportedOperationException("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun setNumericShortcut(numericChar: Char): MenuItem {
        throw UnsupportedOperationException("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

}
