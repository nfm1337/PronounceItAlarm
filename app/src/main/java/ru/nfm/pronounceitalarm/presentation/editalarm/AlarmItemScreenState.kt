package ru.nfm.pronounceitalarm.presentation.editalarm

import ru.nfm.pronounceitalarm.domain.entity.Alarm

sealed class AlarmItemScreenState {

    object Initial : AlarmItemScreenState()

    data class EditAlarmItem(val alarm: Alarm) : AlarmItemScreenState()

    object CreateAlarmItem : AlarmItemScreenState()

}