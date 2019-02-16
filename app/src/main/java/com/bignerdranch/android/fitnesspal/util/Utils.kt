package com.bignerdranch.android.fitnesspal.util

import java.text.SimpleDateFormat
import java.util.*

object Utils {

    private val dateFormat = SimpleDateFormat("dd.MM.yyyy")

    fun dateToMillis(date: String): Long {
        return dateFormat.parse(date).time
    }

    fun millisToDate(millis: Long?): String {
        return dateFormat.format(Date(millis!!))
    }
}
