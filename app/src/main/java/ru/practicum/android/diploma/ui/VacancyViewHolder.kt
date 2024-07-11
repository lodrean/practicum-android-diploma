package ru.practicum.android.diploma.ui

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.domain.models.Vacancy
import java.text.DecimalFormat
import java.util.Currency
import java.util.Locale

class VacancyViewHolder(itemView: View) : ViewHolder(itemView) {

    private val context = itemView.context

    private val vacancyIcon = itemView.findViewById<ImageView>(R.id.vacancy_icon)
    private val vacancyTitle = itemView.findViewById<TextView>(R.id.vacancy_title)
    private val companyName = itemView.findViewById<TextView>(R.id.company_name)
    private val salaryRange = itemView.findViewById<TextView>(R.id.salary_range)

    fun bind(vacancy: Vacancy) {
        Glide.with(context)
            .load(vacancy.employerLogoPath)
            .placeholder(R.drawable.placeholder)
            .centerInside()
            .transform(RoundedCorners(context.resources.getDimensionPixelSize(R.dimen.size_l)))
            .into(vacancyIcon)

        vacancyTitle.text = formatVacancyTitle(vacancy)

        companyName.text = vacancy.employerName

        salaryRange.text = formatSalary(vacancy)
    }

    private fun formatVacancyTitle(vacancy: Vacancy): String {
        return String.format(context.getString(R.string.vacancy_title_template), vacancy.name, vacancy.employerCity)
    }

    private fun formatSalary(vacancy: Vacancy): String {
        val currency = Currency.getInstance(vacancy.salaryCurrencyName)

        val symbol = currency.getSymbol(Locale.getDefault(Locale.Category.DISPLAY))

        val decimalFormat = DecimalFormat("#,###.##")

        var formattedString = ""

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
        }

        return formattedString
    }
}
