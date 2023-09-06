package ru.nfm.pronounceitalarm.domain

import kotlinx.coroutines.flow.Flow

interface AlarmRepository {

    fun getAllAlarms(): Flow<List<Alarm>>
    fun getAlarmById(alarmId: Int): Flow<Alarm?>
    suspend fun insert(alarm: Alarm)
    suspend fun update(alarm: Alarm)
    suspend fun delete(alarm: Alarm)

    //TODO: ONLY FOR TESTING DELETE FOR PROD
    suspend fun populateTestData()
}