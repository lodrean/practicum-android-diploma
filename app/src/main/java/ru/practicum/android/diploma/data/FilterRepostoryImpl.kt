package ru.practicum.android.diploma.data

import android.content.Context
import com.google.gson.Gson
import ru.practicum.android.diploma.domain.models.Filter

class FilterRepostoryImpl(val context: Context) : FilterRepository {
    private val sharedPreferences = context.getSharedPreferences("filter_storage", Context.MODE_PRIVATE)

    override fun loadFilter(): Filter = Gson().fromJson(
        sharedPreferences.getString(CURRENT_FILTER, ""),
        Filter::class.java
    ) ?: Filter()

    override fun saveFilter(filter: Filter) {
        sharedPreferences
            .edit()
            .putString(CURRENT_FILTER, Gson().toJson(filter))
            .apply()
    }

    override fun loadAppliedFilter(): Filter = Gson().fromJson(
        sharedPreferences.getString(APPLIED_FILTER, ""),
        Filter::class.java
    ) ?: Filter()

    override fun saveAppliedFilter(filter: Filter) {
        sharedPreferences
            .edit()
            .putString(APPLIED_FILTER, Gson().toJson(filter))
            .apply()
    }

    companion object {
        const val APPLIED_FILTER = "APPLIED_FILTER"
        const val CURRENT_FILTER = "CURRENT_FILTER"
    }
}
