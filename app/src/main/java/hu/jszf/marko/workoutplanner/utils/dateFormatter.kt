package hu.jszf.marko.workoutplanner.utils

import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

object DateFormatter {
    private val formatter = SimpleDateFormat("yyyy-MM-dd", Locale.GERMAN)

    fun format(date: Date): String {
        return formatter.format(date)
    }

    fun format(calendar: Calendar): String {
        return formatter.format(calendar.time)
    }
}