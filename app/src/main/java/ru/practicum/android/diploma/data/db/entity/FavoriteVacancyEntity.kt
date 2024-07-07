package ru.practicum.android.diploma.data.db.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "favorite_vacancies_table")
data class FavoriteVacancyEntity(
    @PrimaryKey @ColumnInfo(name = "vacancy_id") val vacancyId: Int,
    @ColumnInfo(name = "contacts") val contacts: String?,
    @ColumnInfo(name = "description") val description: String,
    @ColumnInfo(name = "employer_name") val employerName: String,
    @ColumnInfo(name = "employer_logo_path") val employerLogoPath: String,
    @ColumnInfo(name = "experience_name") val experienceName: String,
    @ColumnInfo(name = "key_skills") val keySkills: String,
    @ColumnInfo(name = "name") val name: String,
    @ColumnInfo(name = "salary_currency_name") val salaryCurrencyName: String?,
    @ColumnInfo(name = "salary_from") val salaryFrom: Int?,
    @ColumnInfo(name = "salary_gross") val salaryGross: Boolean?,
    @ColumnInfo(name = "salary_to") val salaryTo: Int?,
    @ColumnInfo(name = "schedule") val schedule: String,
)
