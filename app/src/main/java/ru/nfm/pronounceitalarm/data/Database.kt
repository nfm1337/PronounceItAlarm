package ru.nfm.pronounceitalarm.data

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import ru.nfm.pronounceitalarm.data.dao.AlarmDao
import ru.nfm.pronounceitalarm.data.entity.AlarmDbModel
import ru.nfm.pronounceitalarm.data.entity.SentenceDbModel
import ru.nfm.pronounceitalarm.data.entity.SentencePackDbModel

@Database(
    entities = [
        AlarmDbModel::class,
        SentenceDbModel::class,
        SentencePackDbModel::class
    ],
    version = 2, exportSchema = true
)
@TypeConverters(DayOfWeekConverter::class)
abstract class Database : RoomDatabase() {
    abstract fun alarmDao(): AlarmDao
}