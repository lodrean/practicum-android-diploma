package ru.practicum.android.diploma.domain

import ru.practicum.android.diploma.domain.models.Area
import ru.practicum.android.diploma.domain.models.Filter
import ru.practicum.android.diploma.domain.models.Industry

interface FilterInteractor {
    fun filter(): Filter
    fun setSearchText(text: String)
    fun setArea(area: Area?)
    fun setIndustry(industry: Industry?)
    fun setOnlyWithSalary(onlyWithSalary: Boolean)
}
