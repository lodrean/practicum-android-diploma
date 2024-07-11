package ru.practicum.android.diploma.domain.api

import ru.practicum.android.diploma.domain.models.Vacancy

data class VacanciesSearchResult(
    val vacancies: List<Vacancy>,
    val page: Int,
    val perPage: Int,
    val count: Int,
)
