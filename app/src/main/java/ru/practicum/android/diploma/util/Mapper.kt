package ru.practicum.android.diploma.util

import ru.practicum.android.diploma.data.db.entity.FavoriteVacancyEntity
import ru.practicum.android.diploma.domain.models.Vacancy

fun FavoriteVacancyEntity.toVacancy() = Vacancy(
    id = vacancyId,
    name = name,
    employerName = employerName,
    employerLogoPath = employerLogoPath,
    description = description,
    contactsEmail = contactsEmail,
    contactsName = contactsName,
    contactsPhones = contactsPhones,
    experienceName = experienceName,
    keySkills = keySkills,
    salaryFrom = salaryFrom,
    salaryTo = salaryTo,
    salaryGross = salaryGross,
    salaryCurrencyName = salaryCurrencyName,
    schedule = schedule,
    callTrackingEnabled = callTrackingEnabled,
)

fun Vacancy.toEntity() = FavoriteVacancyEntity(
    vacancyId = id,
    name = name,
    employerName = employerName,
    employerLogoPath = employerLogoPath,
    description = description,
    contactsEmail = contactsEmail,
    contactsName = contactsName,
    contactsPhones = contactsPhones,
    experienceName = experienceName,
    keySkills = keySkills,
    salaryFrom = salaryFrom,
    salaryTo = salaryTo,
    salaryGross = salaryGross,
    salaryCurrencyName = salaryCurrencyName,
    schedule = schedule,
    callTrackingEnabled = callTrackingEnabled,
)
