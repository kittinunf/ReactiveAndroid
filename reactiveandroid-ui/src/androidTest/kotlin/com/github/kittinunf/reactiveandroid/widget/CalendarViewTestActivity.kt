package com.github.kittinunf.reactiveandroid.widget

import android.app.Activity
import android.os.Bundle
import android.widget.CalendarView
import android.widget.LinearLayout
import java.util.*

class CalendarViewTestActivity : Activity() {

    lateinit var calendarView: CalendarView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        calendarView = CalendarView(this).apply {
            val cal = Calendar.getInstance()
            cal.set(2017, Calendar.JANUARY, 1, 0, 0)
            val min = cal.time
            cal.set(2018, Calendar.JANUARY, 1, 0, 0)
            val max = cal.time
            minDate = min.time
            maxDate = max.time
        }

        val parent = LinearLayout(this)
        parent.addView(calendarView)
        setContentView(parent)
    }

}
