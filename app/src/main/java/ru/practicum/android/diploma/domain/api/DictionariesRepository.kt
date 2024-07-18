package ru.practicum.android.diploma.domain.api


import kotlinx.coroutines.flow.Flow
import ru.practicum.android.diploma.domain.models.Area
import ru.practicum.android.diploma.domain.models.Industry
import ru.practicum.android.diploma.util.Resource

interface DictionariesRepository {

    fun getCountries(): Flow<Resource<List<Area>>>

    fun getRegionsByCountry(country: Area): Flow<Resource<List<Area>>>

    fun getIndustries(): Flow<Resource<List<Industry>>>

}
