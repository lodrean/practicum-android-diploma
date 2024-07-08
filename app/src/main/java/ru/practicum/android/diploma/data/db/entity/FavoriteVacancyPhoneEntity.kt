package ru.practicum.android.diploma.data.db.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.ForeignKey.Companion.CASCADE
import androidx.room.PrimaryKey
import org.jetbrains.annotations.Nullable

@Entity(
    tableName = "favorite_vacancy_phone_table", foreignKeys = [ForeignKey(
        entity = FavoriteVacancyEntity::class,
        parentColumns = ["vacancy_id"],
        childColumns = ["vacancy_id"],
        onDelete = CASCADE
    )]
)
data class FavoriteVacancyPhoneEntity(
    @PrimaryKey @ColumnInfo(name = "id") val id: Int,
    @ColumnInfo(name = "vacancy_id") val vacancyId: Int,
    @ColumnInfo(name = "city") val city: String,
    @ColumnInfo(name = "comment") @Nullable val comment: String?,
    @ColumnInfo(name = "country") val country: String,
    @ColumnInfo(name = "formatted") val formatted: String,
    @ColumnInfo(name = "number") val number: String
)
