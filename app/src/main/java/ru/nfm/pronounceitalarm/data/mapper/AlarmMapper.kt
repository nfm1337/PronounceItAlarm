package ru.nfm.pronounceitalarm.data.mapper

import android.icu.text.SimpleDateFormat
import ru.nfm.pronounceitalarm.data.entity.AlarmDbModel
import ru.nfm.pronounceitalarm.domain.Alarm
import ru.nfm.pronounceitalarm.domain.DayOfWeek
import java.util.Calendar
import java.util.Date
import java.util.Locale
import javax.inject.Inject


class AlarmMapper @Inject constructor() {

    fun mapDbModelToEntity(alarmDbModel: AlarmDbModel): Alarm {
        return Alarm(
            id = alarmDbModel.id,
            name = alarmDbModel.name,
            time = convertTimeStampToString(alarmDbModel.timeInMills),
            activeDays = convertDayOfWeekListToActiveDaysMap(alarmDbModel.activeDays),
            isEnabled = alarmDbModel.isEnabled
        )
    }

    fun mapListDbModelToListEntity(alarmDbModelList: List<AlarmDbModel>): List<Alarm> {
        return alarmDbModelList.map { mapDbModelToEntity(it) }
    }

    fun mapEntityToDbModel(alarm: Alarm) : AlarmDbModel {
        return AlarmDbModel(
            id = alarm.id,
            name = alarm.name,
            timeInMills = convertTimeStringToMillis(alarm.time),
            activeDays = convertActiveDaysMapToDayOfWeekList(alarm.activeDays),
            isEnabled = alarm.isEnabled
        )
    }

    private fun convertTimeStampToString(timestamp: Long): String {
        val dateFormat = SimpleDateFormat("HH:mm", Locale.getDefault())
        val calendar = Calendar.getInstance()
        calendar.timeInMillis = timestamp
        return dateFormat.format(calendar.time)
    }

    private fun convertTimeStringToMillis(timeString: String): Long {
        val format = SimpleDateFormat("HH:mm", Locale.getDefault())
        val date: Date = format.parse(timeString) ?: Date()
        return date.time
    }

    private fun convertDayOfWeekListToActiveDaysMap(
        activeDaysList: List<DayOfWeek>
    ) : Map<DayOfWeek, Boolean> {
        return DayOfWeek.values().associateWith { activeDaysList.contains(it) }
    }

    private fun convertActiveDaysMapToDayOfWeekList(
        activeDaysMap: Map<DayOfWeek, Boolean>
    ): List<DayOfWeek> {
        return activeDaysMap.filter { it.value }.keys.toList()
    }
}