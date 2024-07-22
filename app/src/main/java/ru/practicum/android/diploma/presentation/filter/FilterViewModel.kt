package ru.practicum.android.diploma.presentation.filter

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import ru.practicum.android.diploma.domain.FilterInteractor
import ru.practicum.android.diploma.domain.models.Filter

class FilterViewModel(private val filterInteractor: FilterInteractor, application: Application) :
    AndroidViewModel(application) {
    private var currentFilter = Filter()
    private var nextFilter = Filter()

    private val stateLiveData = MutableLiveData<FilterState>()
    fun observeState(): LiveData<FilterState> = stateLiveData

    init {
        currentFilter = filterInteractor.currentFilter()

        if (checkNull(currentFilter)) {
            renderState(FilterState.Default)
        } else {
            fillData(currentFilter)
        }
    }

    private fun renderState(state: FilterState) {
        stateLiveData.postValue(state)
    }

    fun clearFilter() {
        filterInteractor.setOnlyWithSalary(false)
        filterInteractor.setIndustry(null)
        filterInteractor.setArea(null)
        filterInteractor.setSalary(null)
        filterInteractor.apply()
        currentFilter = filterInteractor.currentFilter()
        renderState(FilterState.Default)
    }

    fun setSalaryIsRequired(required: Boolean) {
        filterInteractor.setOnlyWithSalary(required)
    }

    fun setSalary(salary: String) {
        if (salary.isEmpty()) {
            clearSalary()
        } else {
            filterInteractor.setSalary(salary)
        }
    }

    fun clearWorkplace() {
        filterInteractor.setArea(null)
    }

    fun clearIndustry() {
        filterInteractor.setIndustry(null)
    }

    fun resetTheChanges() {
        filterInteractor.restore()
    }

    fun clearSalary() {
        filterInteractor.setSalary(null)
    }

    fun checkNewFilter() {
        nextFilter = filterInteractor.newFilter()
        currentFilter = filterInteractor.currentFilter()

        if (currentFilter != nextFilter) {
            fillData(nextFilter)
        } else {
            checkCurrentFilter(currentFilter)
        }
    }

    private fun checkCurrentFilter(filter: Filter) {
        if (checkNull(filter)) {
            renderState(FilterState.Default)
        } else {
            renderState(FilterState.Filtered(filter))
        }
    }

    private fun checkNull(filter: Filter) =
        filter.area == null && filter.industry == null && filter.salary == null && !filter.onlyWithSalary

    private fun fillData(filter: Filter) {
        renderState(FilterState.Filtered(filter))
    }

    fun checkSaveButton() {
        if (checkParameters()) {
            showSaveButton(false)
        } else {
            showSaveButton(true)
        }

    }

    private fun checkParameters(): Boolean {
        nextFilter = filterInteractor.newFilter()
        return checkNull(nextFilter)
    }

    private fun showSaveButton(showButton: Boolean) {
        renderState(FilterState.readyToSave(showButton))
    }

    fun saveNewFilter() {
        filterInteractor.apply()
    }
}
