package ru.practicum.android.diploma.presentation.workplace

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ru.practicum.android.diploma.domain.FilterInteractor
import ru.practicum.android.diploma.domain.api.DictionariesInteractor
import ru.practicum.android.diploma.domain.models.Area
import ru.practicum.android.diploma.util.Resource

class WorkplaceViewModel(
    private val filterInteractor: FilterInteractor,
    private val dictionariesInteractor: DictionariesInteractor
) : ViewModel() {

    private val workplaceStateLiveData = MutableLiveData<WorkplaceState>(WorkplaceState.NothingIsPicked)
    fun getWorkplaceStateLiveData(): LiveData<WorkplaceState> = workplaceStateLiveData

    fun loadFilter() {
        val filterCountry = filterInteractor.currentFilter().country
        val filterArea = filterInteractor.currentFilter().area

        if (filterCountry == null && filterArea != null) {
            viewModelScope.launch {
                val country = loadCountryByRegion(filterArea)
                filterInteractor.setCountry(country)
                filterInteractor.apply()
                workplaceStateLiveData.postValue(
                    WorkplaceState.CountryAndRegionIsPicked(
                        country, filterArea
                    )
                )
            }
        } else if (filterCountry == null) {
            workplaceStateLiveData.postValue(WorkplaceState.NothingIsPicked)
        } else {
            if (filterArea?.parentId.isNullOrEmpty()) {
                workplaceStateLiveData.postValue(WorkplaceState.CountryIsPicked(filterCountry))
            } else {
                workplaceStateLiveData.postValue(
                    WorkplaceState.CountryAndRegionIsPicked(
                        filterCountry, filterArea!!
                    )
                )
            }
        }
    }

    private suspend fun loadCountryByRegion(region: Area): Area {
        var newArea: Area = region

        while (!newArea.parentId.isNullOrEmpty()) {
            dictionariesInteractor.getAreasById(newArea.parentId!!).collect {
                when (it) {
                    is Resource.Success -> {
                        newArea = it.data!!
                    }

                    is Resource.Error -> {
                        newArea = region
                    }
                }
            }
        }

        return newArea
    }

    fun deleteRegion() {
        if (workplaceStateLiveData.value is WorkplaceState.CountryAndRegionIsPicked) {
            val country = (workplaceStateLiveData.value as WorkplaceState.CountryAndRegionIsPicked).country
            workplaceStateLiveData.postValue(WorkplaceState.CountryIsPicked(country))
            filterInteractor.setArea(country)
            filterInteractor.apply()
        }
    }

    fun deleteCountry() {
        if (workplaceStateLiveData.value is WorkplaceState.CountryIsPicked ||
            workplaceStateLiveData.value is WorkplaceState.CountryAndRegionIsPicked
        ) {
            workplaceStateLiveData.postValue(WorkplaceState.NothingIsPicked)
            filterInteractor.setCountry(null)
            filterInteractor.setArea(null)
            filterInteractor.apply()
        }
    }
}
