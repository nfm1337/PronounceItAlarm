package ru.nfm.pronounceitalarm.data

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import ru.nfm.pronounceitalarm.data.dao.AlarmDao
import ru.nfm.pronounceitalarm.data.entity.AlarmDbModel
import ru.nfm.pronounceitalarm.data.mapper.AlarmMapper
import ru.nfm.pronounceitalarm.domain.Alarm
import ru.nfm.pronounceitalarm.domain.AlarmRepository
import ru.nfm.pronounceitalarm.domain.DayOfWeek
import javax.inject.Inject

class AlarmRepositoryImpl @Inject constructor(
    private val alarmDao: AlarmDao,
    private val mapper: AlarmMapper
) : AlarmRepository {

    override fun getAllAlarms(): Flow<List<Alarm>> {
        return alarmDao.getAllAlarms()
            .map { alarmDbEntityList ->
                mapper.mapListDbModelToListEntity(alarmDbEntityList)
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

    override fun getAlarmById(alarmId: Int): Flow<Alarm?> {
        return alarmDao.getAlarmById(alarmId)
            .map { alarmDbEntity ->
                alarmDbEntity?.let { mapper.mapDbModelToEntity(it) }
            }
    }

    //TODO: ONLY FOR TESTING DELETE FOR PROD
    override suspend fun populateTestData() {
        alarmDao.clearAllAlarms()

        val alarm1 = AlarmDbModel(
            name = "Alarm1",
            timeInMills = System.currentTimeMillis() + 1000 * 60 * 60,
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
            timeInMills = System.currentTimeMillis(),
            activeDays = DayOfWeek.values().toList(),
            isEnabled = true
        )

        alarmDao.insert(alarm1)
        alarmDao.insert(alarm2)
        alarmDao.insert(alarm3)
        alarmDao.insert(alarm4)
    }
}