package ru.practicum.android.diploma.domain.api

import kotlinx.coroutines.flow.Flow
import ru.practicum.android.diploma.domain.models.Area
import ru.practicum.android.diploma.domain.models.Industry
import ru.practicum.android.diploma.util.Resource

interface DictionariesInteractor {

    fun getAreas(): Flow<Resource<List<Area>>>

    fun getAreasById(areaId: String): Flow<Resource<List<Area>>>

    fun getIndustries(): Flow<Resource<List<Industry>>>

}
