package ru.practicum.android.diploma.domain.impl

import ru.practicum.android.diploma.domain.api.VacanciesInteractor
import ru.practicum.android.diploma.domain.api.VacanciesRepository
import ru.practicum.android.diploma.domain.models.Vacancy

class VacanciesInteractorImpl(private val repository: VacanciesRepository) : VacanciesInteractor {

    override fun searchVacancies(expression: String, page: Int, perPage: Int) =
        repository.searchVacancies(expression, page, perPage)

    override fun updateToFullVacancy(vacancy: Vacancy) = repository.updateToFullVacancy(vacancy)

}
