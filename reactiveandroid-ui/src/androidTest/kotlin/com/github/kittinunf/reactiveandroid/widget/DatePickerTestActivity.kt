package com.github.kittinunf.reactiveandroid.widget

import android.app.Activity
import android.os.Bundle
import android.widget.DatePicker
import android.widget.LinearLayout

class DatePickerTestActivity : Activity() {

    lateinit var datePicker: DatePicker

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        datePicker = DatePicker(this)
        datePicker.id = android.R.id.input

        val parent = LinearLayout(this)
        parent.addView(datePicker)
        setContentView(parent)
    }

}
