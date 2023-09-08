package ru.nfm.pronounceitalarm.data

import androidx.room.TypeConverter
import ru.nfm.pronounceitalarm.domain.entity.DayOfWeek

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