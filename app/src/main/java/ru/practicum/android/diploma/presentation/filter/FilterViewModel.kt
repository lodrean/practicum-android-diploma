package ru.practicum.android.diploma.presentation.filter

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

class FilterViewModel(application: Application) :
    AndroidViewModel(application) {
    private var area = "Москва"
    private var industry = "IT"
    private var salary = "50000"
    private var salaryIsRequired = true
    private val stateLiveData = MutableLiveData<FilterState>()
    fun observeState(): LiveData<FilterState> = stateLiveData

    init {
        fillData()
    }
    private fun renderState(state: FilterState) {
        stateLiveData.postValue(state)
    }

    fun clearFilter() {
        renderState(FilterState.Default)
    }

    fun setSalaryIsRequired(required: Boolean) {
        salaryIsRequired = required
    }

    fun setSalary(salary: String) {
        this.salary = salary
    }

    fun saveFilter(
        workPlace: String,
        industry: String,
        salary: String,
        checkSalaryRequired: Boolean
    ) {
        // todo
    }

    fun clearWorkplace() {
        area = ""
    }

    fun clearIndustry() {
        industry = ""
    }
    fun clearSalary() {
        salary = ""
    }

    private fun fillData() {
        renderState(FilterState.Filtered(area, industry, salary, salaryIsRequired))
    }

    fun showSaveButton() {
        renderState(FilterState.readyToSave)
    }

}
