package ru.practicum.android.diploma.ui.viewholder

import android.view.View
import ru.practicum.android.diploma.ui.search.VacancyListItemUiModel

class LoadingViewHolder(itemView: View) : ListItemViewHolder(itemView) {

    override fun bind(listItem: VacancyListItemUiModel) {
        require(listItem is VacancyListItemUiModel.Loading)
        { "Expected VacancyListItemUiModel.Loading" }

    }

}
