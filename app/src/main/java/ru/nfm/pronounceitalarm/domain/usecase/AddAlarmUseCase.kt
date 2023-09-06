package ru.nfm.pronounceitalarm.domain.usecase

import ru.nfm.pronounceitalarm.domain.Alarm
import ru.nfm.pronounceitalarm.domain.AlarmRepository
import javax.inject.Inject

class AddAlarmUseCase @Inject constructor(
    private val repository: AlarmRepository
){

    suspend operator fun invoke(alarm: Alarm) {
        repository.insert(alarm)
    }
}