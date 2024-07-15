package ru.practicum.android.diploma.presentation.favorites

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import ru.practicum.android.diploma.domain.api.FavoritesInteractor
import ru.practicum.android.diploma.util.Resource

class FavoritesViewModel(
    private val favoritesInteractor: FavoritesInteractor
) : ViewModel() {

    private val stateLiveData = MutableLiveData<FavoritesState>()
    fun getStateLiveData(): LiveData<FavoritesState> = stateLiveData

    private fun renderState(state: FavoritesState) {
        stateLiveData.postValue(state)
    }

    fun updateFavoritesList() {
        viewModelScope.launch(Dispatchers.IO) {
            renderState(FavoritesState.Loading)
            favoritesInteractor.getFavoriteVacancies().collect {
                when (it) {
                    is Resource.Success -> {
                        if (it.data!!.isEmpty()) {
                            renderState(FavoritesState.Empty)
                        } else {
                            renderState(FavoritesState.Content(it.data))
                        }
                    }

                    is Resource.Error -> {
                        renderState(FavoritesState.Error)
                    }
                }
            }
        }
    }

}
