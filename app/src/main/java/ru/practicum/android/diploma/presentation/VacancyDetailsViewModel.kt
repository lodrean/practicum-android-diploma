package ru.practicum.android.diploma.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ru.practicum.android.diploma.domain.SharingInteractor
import ru.practicum.android.diploma.domain.api.VacanciesInteractor
import ru.practicum.android.diploma.domain.models.Vacancy
import ru.practicum.android.diploma.ui.vacancy.VacancyDetailsState

class VacancyDetailsViewModel(
    vacancy: Vacancy,
    private val vacanciesInteractor: VacanciesInteractor,
    private val sharingInteractor: SharingInteractor
) : ViewModel() {
    private val stateLiveData = MutableLiveData<VacancyDetailsState>()
    private val stateFavoriteData = MutableLiveData<Boolean>()

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

    fun observeFavoriteState(): LiveData<Boolean> = stateFavoriteData

    private fun renderState(state: VacancyDetailsState) {
        stateLiveData.postValue(state)
    }

    private fun renderFavoriteState(isLiked: Boolean) {
        stateFavoriteData.postValue(isLiked)
    }

    fun shareVacancy(vacancyId: String) {
        sharingInteractor.shareVacancy(vacancyId)
    }

    fun favoriteVacancy() {
        renderFavoriteState(!(observeFavoriteState().value ?: false))
    }
}
