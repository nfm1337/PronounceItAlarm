package ru.nfm.pronounceitalarm.presentation.main

import ru.nfm.pronounceitalarm.domain.Alarm

sealed class MainScreenState {

    object Initial : MainScreenState()

    data class Alarms(val alarms: List<Alarm>) : MainScreenState()
}
