package ru.nfm.pronounceitalarm.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "sentence_pack")
data class SentencePackDbModel(
    @PrimaryKey(autoGenerate = true) val id: Int = UNDEFINED_ID,
    val name: String,
    val description: String,
    val price: Double,
    val isAvailable: Boolean
) {
    companion object {
        private const val UNDEFINED_ID = 0
    }
}
