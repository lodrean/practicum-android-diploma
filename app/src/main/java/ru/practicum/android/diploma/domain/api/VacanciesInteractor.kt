package ru.practicum.android.diploma.domain.api

import kotlinx.coroutines.flow.Flow
import ru.practicum.android.diploma.domain.models.Filter
import ru.practicum.android.diploma.domain.models.Vacancy
import ru.practicum.android.diploma.util.Resource

interface VacanciesInteractor {

    fun searchVacancies(
        expression: String,
        filter: Filter,
        page: Int = 0,
        perPage: Int = 20
    ): Flow<Resource<VacanciesSearchResult>>

    fun updateToFullVacancy(vacancy: Vacancy): Flow<Resource<Vacancy>>

}
