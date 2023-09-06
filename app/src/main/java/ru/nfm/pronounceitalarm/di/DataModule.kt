package ru.nfm.pronounceitalarm.di

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import ru.nfm.pronounceitalarm.data.AlarmRepositoryImpl
import ru.nfm.pronounceitalarm.data.Database
import ru.nfm.pronounceitalarm.data.dao.AlarmDao
import ru.nfm.pronounceitalarm.data.mapper.AlarmMapper
import ru.nfm.pronounceitalarm.domain.AlarmRepository
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DataModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): Database {
        return Room.databaseBuilder(
            context,
            Database::class.java,
            "pronounce_it.db"
        ).build()
    }

    @Provides
    @Singleton
    fun provideAlarmDao(database: Database): AlarmDao {
        return database.alarmDao()
    }

    @Provides
    @Singleton
    fun provideAlarmMapper() : AlarmMapper {
        return AlarmMapper()
    }

    @Provides
    @Singleton
    fun provideRepository(alarmRepositoryImpl: AlarmRepositoryImpl) : AlarmRepository {
        return alarmRepositoryImpl
    }
}