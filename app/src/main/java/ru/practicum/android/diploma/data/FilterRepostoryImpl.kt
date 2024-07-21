package ru.practicum.android.diploma.data

import android.content.Context
import com.google.gson.Gson
import ru.practicum.android.diploma.domain.models.Filter

class FilterRepostoryImpl(val context: Context) : FilterRepository {
    private val sharedPreferences = context.getSharedPreferences("filter_storage", Context.MODE_PRIVATE)

    override fun loadFilter(): Filter = Gson().fromJson(
        sharedPreferences.getString(APP_PREFERENCES, ""),
        Filter::class.java
    ) ?: Filter("")

    override fun saveFilter(filter: Filter) {
        sharedPreferences
            .edit()
            .putString(APP_PREFERENCES, Gson().toJson(filter))
            .apply()
    }

    companion object {
        const val APP_PREFERENCES = "APP_PREFERENCES"
    }
}
