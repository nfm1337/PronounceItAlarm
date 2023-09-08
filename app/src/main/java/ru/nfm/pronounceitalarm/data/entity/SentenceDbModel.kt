package ru.nfm.pronounceitalarm.data.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    tableName = "sentence",
    foreignKeys = [
        ForeignKey(
            entity = SentencePackDbModel::class,
            parentColumns = ["id"],
            childColumns = ["sentencePackId"],
            onDelete = ForeignKey.CASCADE,
            onUpdate = ForeignKey.CASCADE
        )
    ]
)
data class SentenceDbModel(
    @PrimaryKey(autoGenerate = true) val id: Int = UNDEFINED_ID,
    val sentencePackId: Int,
    val text: String
) {
    companion object {
        private const val UNDEFINED_ID = 0
    }
}
