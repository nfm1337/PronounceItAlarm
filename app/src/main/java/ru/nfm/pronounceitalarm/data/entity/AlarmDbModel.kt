package ru.nfm.pronounceitalarm.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import ru.nfm.pronounceitalarm.domain.DayOfWeek

@Entity(tableName = "alarm")
data class AlarmDbModel(
    @PrimaryKey(autoGenerate = true)
    val id: Int = UNDEFINED_ID,
    val name: String,
    val timeInMills: Long,
    val activeDays: List<DayOfWeek>,
    val isEnabled: Boolean
) {
    companion object {
        private const val UNDEFINED_ID = 0
    }
}


