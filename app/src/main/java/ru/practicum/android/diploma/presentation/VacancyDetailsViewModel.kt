package ru.practicum.android.diploma.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ru.practicum.android.diploma.domain.models.Vacancy

class VacancyDetailsViewModel(val vacancy: Vacancy) : ViewModel() {
    private val stateLiveData = MutableLiveData<Result<Vacancy>>()

    init {
        renderState(Result.success(vacancy))
    }

    fun observeState(): LiveData<Result<Vacancy>> = stateLiveData

    private fun renderState(result: Result<Vacancy>) {
        stateLiveData.postValue(result)
    }

    init {
        renderState(Result.success(vacancy))
    }
}
