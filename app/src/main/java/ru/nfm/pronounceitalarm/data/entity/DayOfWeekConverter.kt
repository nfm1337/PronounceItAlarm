package ru.nfm.pronounceitalarm.data.entity

import androidx.room.TypeConverter
import ru.nfm.pronounceitalarm.domain.DayOfWeek

class DayOfWeekConverter {
    @TypeConverter
    fun fromList(value: List<DayOfWeek>): String {
        return value.joinToString(",")
    }

    @TypeConverter
    fun toList(value: String): List<DayOfWeek> {
        return value.split(",").map { DayOfWeek.valueOf(it) }
    }
}