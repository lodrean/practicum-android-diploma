package ru.practicum.android.diploma.presentation.industry

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.domain.api.DictionariesInteractor
import ru.practicum.android.diploma.domain.models.Industry
import ru.practicum.android.diploma.ui.industry.IndustryState
import ru.practicum.android.diploma.util.debounce

class IndustryViewModel(
    val dictionariesInteractor: DictionariesInteractor,
    application: Application
) : AndroidViewModel(application) {

    private val stateLiveData = MutableLiveData<IndustryState>()
    private var latestSearchText: String? = null
    fun observeState(): LiveData<IndustryState> = stateLiveData

    fun searchDebounce(changedText: String) {
        if (latestSearchText != changedText) {
            latestSearchText = changedText
            trackSearchDebounce(changedText)
        }
    }

    private val trackSearchDebounce =
        debounce<String>(SEARCH_DEBOUNCE_DELAY, viewModelScope, true) { changedText ->
            searchIndustries(changedText)
        }

    private fun searchIndustries(searchText: String) {
        stateLiveData.postValue(IndustryState.Loading)
        viewModelScope.launch {
            dictionariesInteractor.getIndustries().collect {
                if (it.message != null) {
                    stateLiveData.postValue(IndustryState.Error(message = it.message))
                }
                if (it.data != null) {
                    it.data.let { industries: List<Industry> ->
                        val filteredIndustries = industries.filter { industry: Industry ->
                            industry.name.contains(searchText)
                        }

                        if (filteredIndustries.isEmpty()) {
                            stateLiveData.postValue(
                                IndustryState.Empty(
                                    message = getApplication<Application>().getString(
                                        R.string.industry_not_found
                                    ),
                                )
                            )
                        } else {
                            stateLiveData.postValue(IndustryState.Content(filteredIndustries))
                        }
                    }
                }
            }
        }
    }

    fun loadIndustries() {
        viewModelScope.launch {
            dictionariesInteractor.getIndustries().collect {
                it.let {
                    it.data?.let {
                        stateLiveData.postValue(IndustryState.Content(it))
                    }
                }
            }
        }
    }

    companion object {
        private const val SEARCH_DEBOUNCE_DELAY = 2000L
    }
}