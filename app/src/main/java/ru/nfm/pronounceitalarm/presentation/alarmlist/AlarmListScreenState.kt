package ru.nfm.pronounceitalarm.presentation.alarmlist

import ru.nfm.pronounceitalarm.domain.entity.Alarm

sealed class AlarmListScreenState {

    object Initial : AlarmListScreenState()

    data class Alarms(val alarms: List<Alarm>) : AlarmListScreenState()
}
