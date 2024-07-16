package ru.practicum.android.diploma.presentation.vacancy

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import ru.practicum.android.diploma.domain.SharingInteractor
import ru.practicum.android.diploma.domain.api.FavoritesInteractor
import ru.practicum.android.diploma.domain.api.VacanciesInteractor
import ru.practicum.android.diploma.domain.models.Vacancy
import ru.practicum.android.diploma.ui.vacancy.VacancyDetailsState

class VacancyDetailsViewModel(
    var vacancy: Vacancy,
    private val vacancyNeedToUpdate: Boolean,
    private val vacanciesInteractor: VacanciesInteractor,
    private val sharingInteractor: SharingInteractor,
    private val favoritesInteractor: FavoritesInteractor
) : ViewModel() {

    private val stateLiveData = MutableLiveData<VacancyDetailsState>()
    fun observeState(): LiveData<VacancyDetailsState> = stateLiveData

    init {
        stateLiveData.postValue(VacancyDetailsState.Loading)
        viewModelScope.launch(Dispatchers.IO) {
            if (vacancyNeedToUpdate) {
                vacanciesInteractor.updateToFullVacancy(vacancy).collect {
                    if (it.message != null) {
                        stateLiveData.postValue(VacancyDetailsState.VacancyServerError)
                    } else if (it.data != null) {
                        vacancy = it.data
                        vacancy.isFavorite = favoritesInteractor.checkVacancyIsFavorite(vacancy)
                        stateLiveData.postValue(VacancyDetailsState.Content(vacancy))
                    } else {
                        stateLiveData.postValue(VacancyDetailsState.VacancyNotFoundedError)
                    }
                }
            } else {
                stateLiveData.postValue(VacancyDetailsState.Content(vacancy))
            }
        }
    }

    fun shareVacancy() {
        sharingInteractor.shareVacancy(vacancy.id)
    }

    fun openEmail(mailTo: String, vacancyName: String) {
        sharingInteractor.openEmail(mailTo, vacancyName)
    }

    fun makeVacancyFavorite() {
        viewModelScope.launch() {
            vacancy.isFavorite = !vacancy.isFavorite
            if (vacancy.isFavorite) {
                favoritesInteractor.addVacancyToFavorites(vacancy)
            } else {
                favoritesInteractor.deleteVacancyFromFavorites(vacancy)
            }
            stateLiveData.postValue(VacancyDetailsState.Content(vacancy))
        }
    }

    fun callPhone(phoneNumber: String) {
        sharingInteractor.callPhone(phoneNumber)
    }
}
