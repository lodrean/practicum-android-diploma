package ru.practicum.android.diploma.presentation.region

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ru.practicum.android.diploma.domain.api.DictionariesInteractor
import ru.practicum.android.diploma.domain.models.Area
import ru.practicum.android.diploma.util.Resource

class RegionViewModel(
    private val dictionariesInteractor: DictionariesInteractor
) : ViewModel() {

    private val regionLiveData = MutableLiveData<RegionState>()
    fun getRegionLiveData(): LiveData<RegionState> = regionLiveData

    private val filteredList: MutableList<Area> = ArrayList()

    fun loadRegionsList(countryId: String?) {

        if (countryId != null) {
            viewModelScope.launch {
                val listOfRegions = mutableListOf<Area>()

                dictionariesInteractor.getAreasById(countryId).collect {
                    when (it) {
                        is Resource.Success -> {
                            if (it.data != null) {
                                listOfRegions.addAll(collectAllRegions(it.data))
                                regionLiveData.postValue(RegionState.Content(listOfRegions))
                            } else {
                                regionLiveData.postValue(RegionState.Empty)
                            }
                        }

                        is Resource.Error -> {
                            regionLiveData.postValue(RegionState.Error)
                        }
                    }
                }
            }
        }
    }

    private fun collectAllRegions(listOfRegions: List<Area>): List<Area> {
        val result = mutableListOf<Area>()

        for (region in listOfRegions) {
            result.add(region)
            if (!region.areas.isNullOrEmpty()) {
                for (locality in region.areas) {
                    result.add(locality)
                }
            }
        }

        return result.sortedBy {
            it.name
        }
    }

    fun filter(searchQuery: String?, countryId: String?) {
        filteredList.clear()
        if (searchQuery.isNullOrEmpty()) {
            loadRegionsList(countryId)
        } else {

            val originalList = (regionLiveData.value as RegionState.Content).regionsList

            for (item in originalList) {
                if (item.name.contains(searchQuery, true)) {
                    filteredList.add(item)
                }
            }
            regionLiveData.postValue(RegionState.Content(filteredList))
        }
    }
}
