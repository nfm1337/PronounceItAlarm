package ru.nfm.pronounceitalarm.util

import android.icu.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

object TimeConverter {
    fun convertTimeStampToString(timestamp: Long): String {
        val dateFormat = SimpleDateFormat("HH:mm", Locale.getDefault())
        val calendar = Calendar.getInstance()
        calendar.timeInMillis = timestamp
        return dateFormat.format(calendar.time)
    }

    fun convertTimeStringToMillis(timeString: String): Long {
        val format = SimpleDateFormat("HH:mm", Locale.getDefault())
        val date: Date = format.parse(timeString) ?: Date()
        return date.time
    }
}