package ru.practicum.android.diploma.ui.search

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.domain.api.VacanciesInteractor
import ru.practicum.android.diploma.domain.models.Vacancy
import ru.practicum.android.diploma.util.SingleLiveEvent
import ru.practicum.android.diploma.util.debounce

class SearchViewModel(private val vacanciesInteractor: VacanciesInteractor, application: Application) :
    AndroidViewModel(application) {

    private var isNextPageLoading: Boolean = false
    private var currentPage: Int = 0
    private var maxPage: Int? = null
    private var latestSearchText: String? = null
    private var vacanciesList = mutableListOf<Vacancy>()
    private val trackSearchDebounce =
        debounce<String>(SEARCH_DEBOUNCE_DELAY, viewModelScope, true) { changedText ->
            clearSearch()
            searchVacancies(changedText)
        }
    private val nextPageDebounce = debounce<String>(SEARCH_NEXT_PAGE_DELAY, viewModelScope, false) { searchText ->
        searchRequest(searchText, currentPage)
    }
    private val showToast = SingleLiveEvent<String>()
    private val stateLiveData = MutableLiveData<SearchState>()
    fun observeState(): LiveData<SearchState> = stateLiveData
    fun observeShowToast(): LiveData<String> = showToast
    fun searchDebounce(changedText: String) {
        if (latestSearchText != changedText) {
            latestSearchText = changedText
            trackSearchDebounce(changedText)
        }
    }

    // Функция для пагинации
    private fun searchVacancies(searchText: String) {
        if (this.currentPage == maxPage) {
            return
        } else {
            if (currentPage == 0) {
                renderState(SearchState.Loading)
                searchRequest(searchText, currentPage)
            } else {
                isNextPageLoading = true
                renderState(SearchState.NextPageLoading)
                nextPageDebounce(searchText)
            }
            currentPage += 1
        }
    }

    private fun searchRequest(searchText: String, currentPage: Int) {
        if (searchText.isNotEmpty()) {
            viewModelScope.launch {
                vacanciesInteractor.searchVacancies(
                    searchText,
                    currentPage,
                    PER_PAGE_SIZE
                )
                    .collect { resource ->
                        processResult(
                            resource.data?.vacancies,
                            resource.message,
                            resource.data?.found
                        )
                        maxPage = resource.data?.count
                    }
            }
        }
    }

    private fun processResult(foundVacancies: List<Vacancy>?, errorMessage: String?, countOfVacancies: Int?) {
        if (foundVacancies != null) {
            vacanciesList.addAll(foundVacancies)
        }
        when {
            errorMessage != null -> {
                when (errorMessage) {
                    getApplication<Application>().getString(R.string.check_connection_message) -> {
                        when (isNextPageLoading) {
                            true -> {
                                renderState(SearchState.Content(vacanciesList, null))
                                showToast(errorMessage)
                                return
                            }

                            false -> renderState(
                                SearchState.NoInternet(
                                    errorMessage = getApplication<Application>()
                                        .getString(
                                            R.string.internet_is_not_available
                                        )
                                ),
                            )
                        }
                    }

                    else -> {
                        renderState(
                            SearchState.Error(
                                errorMessage = getApplication<Application>().getString(R.string.server_error)
                            ),
                        )
                    }
                }
                showToast(errorMessage)
            }

            vacanciesList.isEmpty() -> {
                renderState(
                    SearchState.Empty(
                        message = getApplication<Application>().getString(R.string.not_get_a_list_of_vacancies),
                    )
                )
            }

            else -> {
                renderState(
                    SearchState.Content(
                        vacanciesList.distinct(),
                        countOfVacancies
                    )
                )
                isNextPageLoading = false
            }
        }
    }

    private fun renderState(state: SearchState) {
        stateLiveData.postValue(state)
    }

    private fun showToast(message: String) {
        showToast.postValue(message)
    }

    fun clearSearch() {
        renderState(SearchState.Default)
        currentPage = 0
        maxPage = null
        vacanciesList.clear()
    }

    fun onLastItemReached() {
        if (isNextPageLoading) {
            return
        } else {
            searchVacancies(latestSearchText!!)
        }
    }

    companion object {
        private const val SEARCH_NEXT_PAGE_DELAY = 300L
        private const val SEARCH_DEBOUNCE_DELAY = 2000L
        private const val PER_PAGE_SIZE = 20
    }
}
