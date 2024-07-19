package ru.practicum.android.diploma.domain.impl

import kotlinx.coroutines.flow.Flow
import ru.practicum.android.diploma.domain.api.DictionariesInteractor
import ru.practicum.android.diploma.domain.api.DictionariesRepository
import ru.practicum.android.diploma.domain.models.Area
import ru.practicum.android.diploma.domain.models.Industry
import ru.practicum.android.diploma.util.Resource

class DictionariesInteractorImpl(private val repository: DictionariesRepository) : DictionariesInteractor {

    override fun getCountries(): Flow<Resource<List<Area>>> = repository.getCountries()

    override fun getRegionsByCountry(country: Area): Flow<Resource<List<Area>>> =
        repository.getRegionsByCountry(country)

    override fun getIndustries(): Flow<Resource<List<Industry>>> = repository.getIndustries()

}
