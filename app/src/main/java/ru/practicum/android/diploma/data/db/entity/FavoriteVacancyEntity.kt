package ru.practicum.android.diploma.data.db.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import org.jetbrains.annotations.Nullable

@Entity(tableName = "favorite_vacancies_table", indices = [Index("vacancy_id")])
data class FavoriteVacancyEntity(
    @PrimaryKey @ColumnInfo(name = "vacancy_id") val vacancyId: Int,
    @ColumnInfo(name = "call_tracking_enabled") @Nullable val callTrackingEnabled: Boolean?,
    @ColumnInfo(name = "contacts_email") @Nullable val contactsEmail: String?,
    @ColumnInfo(name = "contacts_name") @Nullable val contactsName: String?,
    @ColumnInfo(name = "contacts_phones") @Nullable val contactsPhones: String?,
    @ColumnInfo(name = "description") val description: String,
    @ColumnInfo(name = "employer_name") val employerName: String,
    @ColumnInfo(name = "employer_logo_path") val employerLogoPath: String,
    @ColumnInfo(name = "employer_city") val employerCity: String,
    @ColumnInfo(name = "employment") val employment: String,
    @ColumnInfo(name = "experience_name") val experienceName: String,
    @ColumnInfo(name = "key_skills") val keySkills: String,
    @ColumnInfo(name = "name") val name: String,
    @ColumnInfo(name = "salary_currency_name") @Nullable val salaryCurrencyName: String?,
    @ColumnInfo(name = "salary_from") @Nullable val salaryFrom: Int?,
    @ColumnInfo(name = "salary_gross") @Nullable val salaryGross: Boolean?,
    @ColumnInfo(name = "salary_to") @Nullable val salaryTo: Int?,
    @ColumnInfo(name = "schedule") val schedule: String
)
