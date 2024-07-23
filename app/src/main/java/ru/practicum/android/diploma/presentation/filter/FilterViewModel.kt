package ru.practicum.android.diploma.presentation.filter

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
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
            viewModelScope.launch(Dispatchers.Main) {
                fillData(currentFilter)
            }
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
viewModelScope.launch(Dispatchers.Main) {
    renderState(FilterState.Filtered(currentFilter))
}
        /*renderState(FilterState.Filtered(currentFilter))*/
    }

    fun setSalaryIsRequired(required: Boolean) {
        filterInteractor.setOnlyWithSalary(required)
        filterInteractor.apply()
    }

    fun setSalary(salary: String) {
        if (salary.isEmpty()) {
            clearSalary()
        } else {
            filterInteractor.setSalary(salary)
            filterInteractor.apply()
        }
    }

    fun clearWorkplace() {
        filterInteractor.setArea(null)
        filterInteractor.apply()
    }

    fun clearIndustry() {
        filterInteractor.setIndustry(null)
        filterInteractor.apply()
    }

    fun resetTheChanges() {
        filterInteractor.restore()
    }

    fun clearSalary() {
        filterInteractor.setSalary(null)
        filterInteractor.apply()
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

    /* fun showSaveButton(showSaveButton: Boolean, showResetButton: Boolean) {
         showSaveAndResetButtons(showSaveButton = showSaveButton, showResetButton = showResetButton)
     }*/


    /*private fun showSaveAndResetButtons(showSaveButton: Boolean, showResetButton: Boolean) {
        renderState(FilterState.showButtons(showSaveButton, showResetButton))
    }*/

    fun saveNewFilter() {
        filterInteractor.apply()
    }
}
