package ru.practicum.android.diploma.domain.impl

import ru.practicum.android.diploma.data.FilterRepository
import ru.practicum.android.diploma.domain.FilterInteractor
import ru.practicum.android.diploma.domain.models.Area
import ru.practicum.android.diploma.domain.models.Filter
import ru.practicum.android.diploma.domain.models.Industry

class FilterInteractorImpl(private val repository: FilterRepository) : FilterInteractor {
    private var currentFilter: Filter = repository.loadFilter()
    private var appliedFilter: Filter = repository.loadAppliedFilter()

    override fun currentFilter(): Filter = repository.loadFilter()
    override fun appliedFilter(): Filter = repository.loadAppliedFilter()

    override fun setCountry(country: Area?) {
        currentFilter = repository.loadFilter()
        currentFilter = currentFilter.copy(country = country)
        saveCurrentFilter()
    }

    override fun setArea(area: Area?) {
        currentFilter = repository.loadFilter()
        currentFilter = currentFilter.copy(area = area)
        saveCurrentFilter()
    }

    override fun setIndustry(industry: Industry?) {
        currentFilter = repository.loadFilter()
        currentFilter = currentFilter.copy(industry = industry)
        saveCurrentFilter()
    }

    override fun setSalary(salary: String?) {
        currentFilter = repository.loadFilter()
        currentFilter = currentFilter.copy(salary = salary)
        saveCurrentFilter()
    }

    override fun setOnlyWithSalary(onlyWithSalary: Boolean) {
        currentFilter = repository.loadFilter()
        currentFilter = currentFilter.copy(onlyWithSalary = onlyWithSalary)
        saveCurrentFilter()
    }

    private fun saveCurrentFilter() {
        repository.saveFilter(currentFilter)
    }

    private fun saveAppliedFilter() {
        appliedFilter = repository.loadFilter()
        repository.saveAppliedFilter(appliedFilter)
    }

    override fun apply() {
        currentFilter = repository.loadFilter()
        appliedFilter = repository.loadAppliedFilter()
        appliedFilter = currentFilter
        repository.saveAppliedFilter(appliedFilter)
    }

    override fun flushFilters() {
        currentFilter = Filter()
        appliedFilter = Filter()
        saveCurrentFilter()
        saveAppliedFilter()
    }

    override fun resetCurrentFilter() {
        currentFilter = Filter()
        saveCurrentFilter()
    }
}
