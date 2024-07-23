package ru.practicum.android.diploma.data

import ru.practicum.android.diploma.domain.models.Filter

interface FilterRepository {
    fun loadFilter(): Filter
    fun saveFilter(filter: Filter)
    fun loadAppliedFilter(): Filter
    fun saveAppliedFilter(filter: Filter)
}
