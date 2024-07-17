package ru.practicum.android.diploma.ui.viewholder

import android.view.View
import ru.practicum.android.diploma.ui.ListItemUiModel

class EmptyViewHolder(itemView: View) :
    ListItemViewHolder(itemView) {

    override fun bind(listitem: ListItemUiModel) {
        require(listitem is ListItemUiModel.Empty)
        { "ListItemUiModel.Loading" }
    }
}
