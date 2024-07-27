package ru.practicum.android.diploma.presentation.country

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import ru.practicum.android.diploma.domain.FilterInteractor
import ru.practicum.android.diploma.domain.api.DictionariesInteractor
import ru.practicum.android.diploma.domain.models.Area
import ru.practicum.android.diploma.util.Resource

class CountryViewModel(
    private val filterInteractor: FilterInteractor,
    private val dictionariesInteractor: DictionariesInteractor
) : ViewModel() {

    private val countryLiveData = MutableLiveData<CountryState>()
    fun getCountryLiveData(): LiveData<CountryState> = countryLiveData

    fun loadCountriesList() {
        viewModelScope.launch {
            dictionariesInteractor.getAreas().collect {
                when (it) {
                    is Resource.Success -> {
                        if (it.data != null) {
                            countryLiveData.postValue(CountryState.Content(it.data))
                        } else {
                            countryLiveData.postValue(CountryState.Empty)
                            delay(RETRY_DELAY_TIME)
                            loadCountriesList()
                        }
                    }

                    is Resource.Error -> {
                        countryLiveData.postValue(CountryState.Error)
                        delay(RETRY_DELAY_TIME)
                        loadCountriesList()
                    }
                }
            }
        }
    }

    fun setCountry(country: Area) {
        filterInteractor.setCountry(country)
        filterInteractor.setArea(country)
    }

    companion object {
        private const val RETRY_DELAY_TIME = 1000L
    }
}
