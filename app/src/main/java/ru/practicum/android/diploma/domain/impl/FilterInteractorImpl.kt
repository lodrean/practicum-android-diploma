package ru.practicum.android.diploma.domain.impl

import ru.practicum.android.diploma.data.FilterRepository
import ru.practicum.android.diploma.domain.FilterInteractor
import ru.practicum.android.diploma.domain.models.Area
import ru.practicum.android.diploma.domain.models.Filter
import ru.practicum.android.diploma.domain.models.Industry

class FilterInteractorImpl(private val repository: FilterRepository) : FilterInteractor {
    private var filter = repository.loadFilter()

    override fun filter(): Filter = filter

    override fun setSearchText(text: String) {
        filter = filter.copy(text = text)
        repository.saveFilter(filter)
    }

    override fun setArea(area: Area?) {
        filter = filter.copy(area = area)
        repository.saveFilter(filter)
    }

    override fun setIndustry(industry: Industry?) {
        filter = filter.copy(industry = industry)
        repository.saveFilter(filter)
    }

    override fun setOnlyWithSalary(onlyWithSalary: Boolean) {
        filter = filter.copy(onlyWithSalary = onlyWithSalary)
        repository.saveFilter(filter)
    }
}
