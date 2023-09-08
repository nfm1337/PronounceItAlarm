package ru.nfm.pronounceitalarm.data

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import ru.nfm.pronounceitalarm.data.dao.AlarmDao
import ru.nfm.pronounceitalarm.data.entity.AlarmDbModel
import ru.nfm.pronounceitalarm.data.mapper.AlarmMapper
import ru.nfm.pronounceitalarm.domain.entity.Alarm
import ru.nfm.pronounceitalarm.domain.entity.DayOfWeek
import ru.nfm.pronounceitalarm.domain.repository.AlarmRepository
import javax.inject.Inject

class AlarmRepositoryImpl @Inject constructor(
    private val alarmDao: AlarmDao,
    private val mapper: AlarmMapper
) : AlarmRepository {

    private val coroutineScope = CoroutineScope(Dispatchers.Default)

    override fun getAllAlarms(): Flow<List<Alarm>> = flow {
        alarmDao.getAllAlarms()
            .map { alarmDbEntityList ->
                val list = mapper.mapListDbModelToListEntity(alarmDbEntityList)
                list.sortedWith(compareBy({ it.time }, { it.name }))
                emit(mapper.mapListDbModelToListEntity(alarmDbEntityList))
            }
    }

    override suspend fun insert(alarm: Alarm) {
        val alarmDbEntity = mapper.mapEntityToDbModel(alarm)
        alarmDao.insert(alarmDbEntity)
    }

    override suspend fun update(alarm: Alarm) {
        val alarmDbEntity = mapper.mapEntityToDbModel(alarm)
        alarmDao.update(alarmDbEntity)
    }

    override suspend fun delete(alarm: Alarm) {
        val alarmDbEntity = mapper.mapEntityToDbModel(alarm)
        alarmDao.delete(alarmDbEntity)
    }

    override fun getAlarmById(alarmId: Int): StateFlow<Alarm?> = flow {
        alarmDao.getAlarmById(alarmId)
            .map { alarmDbEntity ->
                alarmDbEntity?.let { emit(mapper.mapDbModelToEntity(it)) }
            }
    }.stateIn(
        scope = coroutineScope,
        started = SharingStarted.Lazily,
        initialValue = Alarm()
    )


    //TODO: ONLY FOR TESTING DELETE FOR PROD
    override suspend fun populateTestData() {
        alarmDao.clearAllAlarms()
        val oneHour = 1000 * 60 * 60

        val alarm1 = AlarmDbModel(
            name = "Alarm1",
            timeInMills = System.currentTimeMillis() + oneHour,
            activeDays = listOf(DayOfWeek.Monday, DayOfWeek.Friday),
            isEnabled = true
        )

        val alarm2 = AlarmDbModel(
            name = "Alarm2",
            timeInMills = System.currentTimeMillis(),
            activeDays = listOf(DayOfWeek.Monday, DayOfWeek.Friday),
            isEnabled = false
        )

        val alarm3 = AlarmDbModel(
            name = "Alarm3",
            timeInMills = System.currentTimeMillis(),
            activeDays = listOf(DayOfWeek.Monday, DayOfWeek.Tuesday, DayOfWeek.Friday),
            isEnabled = true
        )

        val alarm4 = AlarmDbModel(
            name = "Alarm4",
            timeInMills = System.currentTimeMillis() - oneHour,
            activeDays = DayOfWeek.values().toList(),
            isEnabled = true
        )

        alarmDao.insert(alarm1)
        alarmDao.insert(alarm2)
        alarmDao.insert(alarm3)
        alarmDao.insert(alarm4)
    }
}