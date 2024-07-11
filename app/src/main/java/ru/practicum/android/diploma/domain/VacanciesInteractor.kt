package ru.practicum.android.diploma.domain

import kotlinx.coroutines.flow.Flow
import ru.practicum.android.diploma.domain.models.Vacancy

interface VacanciesInteractor {
     fun searchVacancies(newSearchText: String): Flow<Pair<ArrayList<Vacancy>?, String?>>
}
