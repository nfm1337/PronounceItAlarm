package ru.nfm.pronounceitalarm.domain.usecase

import kotlinx.coroutines.flow.Flow
import ru.nfm.pronounceitalarm.domain.Alarm
import ru.nfm.pronounceitalarm.domain.AlarmRepository
import javax.inject.Inject

class GetAlarmsUseCase @Inject constructor(
    private val repository: AlarmRepository
){

    operator fun invoke(): Flow<List<Alarm>> {
        return repository.getAllAlarms()
    }
}