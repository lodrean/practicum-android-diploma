package ru.practicum.android.diploma.util

import android.content.Context
import androidx.core.content.ContextCompat.getString
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.domain.models.Vacancy
import java.text.DecimalFormat
import java.util.Currency

object UtilityFunctions {

    fun formatSalary(vacancy: Vacancy, context: Context): String {
        val currencySymbol: MutableMap<String, String> = hashMapOf(
            context.getString(R.string.eur) to context.getString(R.string.eur_symbol),
            context.getString(R.string.usd) to context.getString(R.string.usd_symbol),
            context.getString(R.string.rur) to context.getString(R.string.rur_symbol),
            context.getString(R.string.rub) to context.getString(R.string.rub_symbol),
            context.getString(R.string.byr) to context.getString(R.string.byr_symbol),
            context.getString(R.string.kzt) to context.getString(R.string.kzt_symbol),
            context.getString(R.string.uah) to context.getString(R.string.uah_symbol),
            context.getString(R.string.azn) to context.getString(R.string.azn_symbol),
            context.getString(R.string.uzs) to context.getString(R.string.uzs_symbol),
            context.getString(R.string.gel) to context.getString(R.string.gel_symbol),
            context.getString(R.string.kgt) to context.getString(R.string.kgt_symbol),
        )
        var symbol = ""
        if (vacancy.salaryCurrencyName != null) {
            val currency = Currency.getInstance(vacancy.salaryCurrencyName)
            symbol = currencySymbol[currency.currencyCode] ?: getString(context, R.string.empty_string)
        }

        val decimalFormat = DecimalFormat("#,###.##")

        val formattedString: String

        if (vacancy.salaryFrom == null && vacancy.salaryTo == null) {
            formattedString =
                context.getString(R.string.salary_not_specified)
        } else if (vacancy.salaryFrom != null && vacancy.salaryTo != null) {
            formattedString = String.format(
                context.getString(R.string.salary_range_from_to),
                decimalFormat.format(vacancy.salaryFrom),
                decimalFormat.format(vacancy.salaryTo),
                symbol
            )
        } else if (vacancy.salaryFrom != null) {
            formattedString = String.format(
                context.getString(R.string.salary_range_from),
                decimalFormat.format(vacancy.salaryFrom),
                symbol
            )
        } else {
            formattedString = String.format(
                context.getString(R.string.salary_range_to),
                decimalFormat.format(vacancy.salaryTo),
                symbol
            )
        }

        return formattedString
    }

}
