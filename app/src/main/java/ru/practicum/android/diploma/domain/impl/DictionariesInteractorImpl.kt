package ru.practicum.android.diploma.domain.impl

import kotlinx.coroutines.flow.Flow
import ru.practicum.android.diploma.domain.api.DictionariesInteractor
import ru.practicum.android.diploma.domain.api.DictionariesRepository
import ru.practicum.android.diploma.domain.models.Area
import ru.practicum.android.diploma.domain.models.Industry
import ru.practicum.android.diploma.util.Resource

class DictionariesInteractorImpl(private val repository: DictionariesRepository) : DictionariesInteractor {

    override fun getAreas(): Flow<Resource<List<Area>>> = repository.getAreas()

    override fun getAreasById(areaId: String): Flow<Resource<List<Area>>> =
        repository.getAreasById(areaId)

    override fun getIndustries(): Flow<Resource<List<Industry>>> = repository.getIndustries()

}
