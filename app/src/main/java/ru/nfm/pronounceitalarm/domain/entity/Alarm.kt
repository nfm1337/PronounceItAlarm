package ru.nfm.pronounceitalarm.domain.entity

import kotlin.random.Random

data class Alarm(
    val id: Int = 0,
    var name: String = "Alarm",
    var time: String = "13:00",
    val activeDays: Map<DayOfWeek, Boolean> = mapOf(Pair(DayOfWeek.Monday, true)),
    val isEnabled: Boolean = Random.nextBoolean()
) {
    val isEveryday: Boolean
        get() = activeDays.size == DayOfWeek.values().size

    val isSingle: Boolean
        get() = activeDays.isEmpty()

}