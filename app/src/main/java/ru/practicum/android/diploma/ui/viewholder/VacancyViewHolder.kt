package ru.practicum.android.diploma.ui.viewholder

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.domain.models.Vacancy
import ru.practicum.android.diploma.ui.search.VacancyListItemUiModel
import ru.practicum.android.diploma.util.OnItemClickListener
import ru.practicum.android.diploma.util.UtilityFunctions

class VacancyViewHolder(itemView: View, private val onClickListener: OnItemClickListener) :
    ListItemViewHolder(itemView) {

    private val context = itemView.context

    private val vacancyIcon = itemView.findViewById<ImageView>(R.id.vacancy_icon)
    private val vacancyTitle = itemView.findViewById<TextView>(R.id.vacancy_title)
    private val companyName = itemView.findViewById<TextView>(R.id.company_name)
    private val salaryRange = itemView.findViewById<TextView>(R.id.salary_range)

    override fun bind(listItem: VacancyListItemUiModel) {
        require(listItem is VacancyListItemUiModel.VacancyItem) { "Expected VacancyListItemUiModel.VacancyItem" }
        val vacancy = listItem.vacancy
        itemView.setOnClickListener { onClickListener.onItemClick(vacancy) }
        Glide.with(context)
            .load(vacancy.employerLogoPath)
            .placeholder(R.drawable.placeholder)
            .centerInside()
            .transform(RoundedCorners(context.resources.getDimensionPixelSize(R.dimen.size_l)))
            .into(vacancyIcon)

        vacancyTitle.text = formatVacancyTitle(vacancy)

        companyName.text = vacancy.employerName

        salaryRange.text = UtilityFunctions.formatSalary(vacancy, context)
    }

    private fun formatVacancyTitle(vacancy: Vacancy): String {
        return String.format(context.getString(R.string.vacancy_title_template), vacancy.name, vacancy.employerCity)
    }
}
