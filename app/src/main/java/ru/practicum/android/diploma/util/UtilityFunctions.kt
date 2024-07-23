package ru.practicum.android.diploma.util

import android.content.Context
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.domain.models.Vacancy
import java.text.DecimalFormat
import java.util.Currency
import java.util.Locale

object UtilityFunctions {

    fun formatSalary(vacancy: Vacancy, context: Context): String {
        var symbol = ""
        if (vacancy.salaryCurrencyName != null) {
            val currency = Currency.getInstance(vacancy.salaryCurrencyName)
            symbol = currency.getSymbol(Locale.getDefault(Locale.Category.DISPLAY))
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
