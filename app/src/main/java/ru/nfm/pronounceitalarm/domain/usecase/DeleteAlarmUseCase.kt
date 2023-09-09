package ru.nfm.pronounceitalarm.domain.usecase

import ru.nfm.pronounceitalarm.domain.entity.Alarm
import ru.nfm.pronounceitalarm.domain.repository.AlarmRepository
import javax.inject.Inject

class DeleteAlarmUseCase @Inject constructor(
    private val repository: AlarmRepository
) {

    suspend operator fun invoke(alarm: Alarm) {
        repository.delete(alarm)
    }
}