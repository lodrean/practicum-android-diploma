package ru.practicum.android.diploma.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ru.practicum.android.diploma.domain.api.VacanciesInteractor
import ru.practicum.android.diploma.domain.models.Vacancy
import ru.practicum.android.diploma.ui.vacancy.VacancyDetailsState

class VacancyDetailsViewModel(vacancy: Vacancy, private val vacanciesInteractor: VacanciesInteractor) : ViewModel() {
    private val stateLiveData = MutableLiveData<VacancyDetailsState>()

    init {
        renderState(VacancyDetailsState.Loading)
        viewModelScope.launch {
            vacanciesInteractor.updateToFullVacancy(vacancy).collect {
                if (it.message != null) {
                    renderState(VacancyDetailsState.VacancyServerError)
                } else if (it.data != null) {
                    renderState(VacancyDetailsState.Content(it.data))
                } else {
                    renderState(VacancyDetailsState.VacancyNotFoundedError)
                }
            }
        }
    }

    fun observeState(): LiveData<VacancyDetailsState> = stateLiveData

    private fun renderState(state: VacancyDetailsState) {
        stateLiveData.postValue(state)
    }
}
