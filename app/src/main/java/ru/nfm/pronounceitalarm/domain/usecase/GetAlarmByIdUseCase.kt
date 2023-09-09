package ru.nfm.pronounceitalarm.domain.usecase

import kotlinx.coroutines.flow.Flow
import ru.nfm.pronounceitalarm.domain.entity.Alarm
import ru.nfm.pronounceitalarm.domain.repository.AlarmRepository
import javax.inject.Inject

class GetAlarmByIdUseCase @Inject constructor(
    private val repository: AlarmRepository
) {

    operator fun invoke(alarmId: Int): Flow<Alarm?> {
        return repository.getAlarmById(alarmId)
    }
}