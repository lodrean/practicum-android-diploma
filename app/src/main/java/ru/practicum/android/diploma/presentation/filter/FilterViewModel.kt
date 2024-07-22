package ru.practicum.android.diploma.presentation.filter

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ru.practicum.android.diploma.domain.FilterInteractor
import ru.practicum.android.diploma.domain.models.Filter

class FilterViewModel(private val filterInteractor: FilterInteractor, application: Application) :
    AndroidViewModel(application) {
    private var currentFilter = Filter()
    private var nextFilter = Filter()
    private var area: String? = null
    private var industry: String? = null
    private var salary: String? = null
    private var onlyWithSalary: Boolean = false

    private val stateLiveData = MutableLiveData<FilterState>()
    fun observeState(): LiveData<FilterState> = stateLiveData

    init {
        viewModelScope.launch {
            currentFilter = filterInteractor.currentFilter()
        }
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
        viewModelScope.launch() {
            filterInteractor.setOnlyWithSalary(false)
            filterInteractor.setIndustry(null)
            filterInteractor.setArea(null)
            filterInteractor.setSalary(null)
            filterInteractor.apply()
            currentFilter = filterInteractor.currentFilter()
            renderState(FilterState.Filtered(currentFilter))
            renderState(FilterState.Default)
        }
    }

    fun setSalaryIsRequired(required: Boolean) {
        onlyWithSalary = required
        viewModelScope.launch {
            filterInteractor.setOnlyWithSalary(required)
        }
    }

    fun setSalary(salary: String) {
        this.salary = salary.ifEmpty { null }
        viewModelScope.launch {
            filterInteractor.setSalary(salary)
        }
    }

    fun clearWorkplace() {
        area = null
        viewModelScope.launch {
            filterInteractor.setArea(null)
        }
    }

    fun clearIndustry() {
        industry = null
        viewModelScope.launch {
            filterInteractor.setIndustry(null)
        }
    }

    fun resetTheChanges() {
        viewModelScope.launch {
            filterInteractor.restore()
        }
    }

    fun clearSalary() {
        salary = null
        viewModelScope.launch {
            filterInteractor.setSalary(null)
        }
    }

    fun checkNewFilter() {
        viewModelScope.launch {
            nextFilter = filterInteractor.newFilter()
            currentFilter = filterInteractor.currentFilter()
        }
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
        viewModelScope.launch {
            if (area.isNullOrEmpty() && industry.isNullOrEmpty() && !onlyWithSalary && salary.isNullOrEmpty()) {
                showSaveButton(false)
            } else {
                showSaveButton(true)
            }
        }
    }

    fun showSaveButton(showButton: Boolean) {
        renderState(FilterState.readyToSave(showButton))
    }

    fun saveNewFilter() {
        viewModelScope.launch {
            filterInteractor.apply()
        }
    }
}
