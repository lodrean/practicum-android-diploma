package ru.practicum.android.diploma.presentation.filter

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

class FilterViewModel(application: Application) :
    AndroidViewModel(application) {
    private var salaryIsRequired = false
    private val stateLiveData = MutableLiveData<FilterState>()
    fun observeState(): LiveData<FilterState> = stateLiveData


    private fun renderState(state: FilterState) {
        stateLiveData.postValue(state)
    }

    fun clearFilter() {
    }

    fun setSalaryIsRequired(required: Boolean) {
        salaryIsRequired = required
    }

    fun saveFilter(
        workPlace: String,
        industry: String,
        salary: String,
        checkSalaryRequired: Boolean) {

    }

}
