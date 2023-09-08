package ru.nfm.pronounceitalarm.domain.repository

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow
import ru.nfm.pronounceitalarm.domain.entity.Alarm

interface AlarmRepository {

    fun getAllAlarms(): Flow<List<Alarm>>
    fun getAlarmById(alarmId: Int): StateFlow<Alarm?>
    suspend fun insert(alarm: Alarm)
    suspend fun update(alarm: Alarm)
    suspend fun delete(alarm: Alarm)

    //TODO: ONLY FOR TESTING. DELETE!!!
    suspend fun populateTestData()
}