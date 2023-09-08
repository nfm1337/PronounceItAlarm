package ru.nfm.pronounceitalarm.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow
import ru.nfm.pronounceitalarm.data.entity.AlarmDbModel

@Dao
interface AlarmDao {

    @Query("SELECT * FROM alarm ORDER BY timeInMills")
    fun getAllAlarms(): Flow<List<AlarmDbModel>>

    @Query("SELECT * FROM alarm WHERE id = :alarmId")
    fun getAlarmById(alarmId: Int): Flow<AlarmDbModel?>

    @Insert
    suspend fun insert(alarm: AlarmDbModel)

    @Update
    suspend fun update(alarm: AlarmDbModel)

    @Delete
    suspend fun delete(alarm: AlarmDbModel)

    @Query("DELETE FROM alarm")
    suspend fun clearAllAlarms()
}