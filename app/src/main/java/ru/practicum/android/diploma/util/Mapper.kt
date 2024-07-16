package ru.practicum.android.diploma.util

import ru.practicum.android.diploma.data.db.entity.FavoriteVacancyEntity
import ru.practicum.android.diploma.data.dto.VacancyDto
import ru.practicum.android.diploma.domain.models.Vacancy

fun FavoriteVacancyEntity.toVacancy() = Vacancy(
    id = vacancyId.toString(), // Наверное нужно в Entity тоже сдеть строку
    name = name,
    employerName = employerName,
    employerLogoPath = employerLogoPath,
    employerCity = employerCity,
    employment = employment,
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
    vacancyId = id.toInt(), // Наверное нужно в Entity тоже сдеть строку
    name = name,
    employerName = employerName,
    employerLogoPath = employerLogoPath ?: "",
    employerCity = employerCity,
    employment = employment,
    description = description,
    contactsEmail = contactsEmail,
    contactsName = contactsName,
    contactsPhones = contactsPhones,
    experienceName = experienceName ?: "",
    keySkills = keySkills ?: "",
    salaryFrom = salaryFrom,
    salaryTo = salaryTo,
    salaryGross = salaryGross,
    salaryCurrencyName = salaryCurrencyName,
    schedule = schedule ?: "",
    callTrackingEnabled = callTrackingEnabled,
)

fun VacancyDto.toVacancy() = Vacancy(
    id = id,
    name = name,
    employerName = employer?.name ?: "",
    employerLogoPath = employer?.logoUrls?.original ?: "",
    employerCity = area.name,
    employment = employment?.name ?: "",
    description = "",
    contactsEmail = contacts?.email,
    contactsName = contacts?.name,
    contactsPhones = contacts?.phones?.joinToString(),
    experienceName = experience?.name,
    keySkills = "",
    salaryFrom = salary?.salaryFrom,
    salaryTo = salary?.salaryTo,
    salaryGross = salary?.gross,
    salaryCurrencyName = salary?.currency,
    schedule = schedule?.name,
    callTrackingEnabled = null,
)
