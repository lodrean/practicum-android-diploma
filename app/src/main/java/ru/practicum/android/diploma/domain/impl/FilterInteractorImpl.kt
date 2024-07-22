package ru.practicum.android.diploma.domain.impl

import ru.practicum.android.diploma.data.FilterRepository
import ru.practicum.android.diploma.domain.FilterInteractor
import ru.practicum.android.diploma.domain.models.Area
import ru.practicum.android.diploma.domain.models.Filter
import ru.practicum.android.diploma.domain.models.Industry

class FilterInteractorImpl(private val repository: FilterRepository) : FilterInteractor {
    private var currentFilter: Filter = repository.loadFilter()
    private var newFilter: Filter = currentFilter

    override fun currentFilter(): Filter = currentFilter
    override fun newFilter(): Filter = newFilter

    override fun setArea(area: Area?) {
        newFilter = newFilter.copy(area = area)
    }

    override fun setIndustry(industry: Industry?) {
        newFilter = newFilter.copy(industry = industry)
    }

    override fun setOnlyWithSalary(onlyWithSalary: Boolean) {
        newFilter = newFilter.copy(onlyWithSalary = onlyWithSalary)
    }

    override fun apply() {
        currentFilter = newFilter
        repository.saveFilter(currentFilter)
    }

    override fun restore() {
        newFilter = currentFilter
    }
}
