package ru.nfm.pronounceitalarm.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow
import ru.nfm.pronounceitalarm.data.entity.SentenceDbModel
import ru.nfm.pronounceitalarm.data.entity.SentencePackDbModel

@Dao
interface SentencePackDao {

    @Query("SELECT * FROM sentence_pack")
    fun getAllSentencePacks(): Flow<List<SentencePackDbModel>>

    @Query("SELECT * FROM sentence WHERE sentencePackId = :packId")
    fun getSentencesForPack(packId: Int): Flow<List<SentenceDbModel>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSentencePack(sentencePack: SentencePackDbModel)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSentence(sentence: SentenceDbModel)
}