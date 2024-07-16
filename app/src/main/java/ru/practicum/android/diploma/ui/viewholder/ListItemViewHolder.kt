package ru.practicum.android.diploma.ui.viewholder

import android.view.View
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import ru.practicum.android.diploma.domain.models.Vacancy

abstract class ListItemViewHolder(itemView: View) : ViewHolder(itemView) {
    abstract fun bind(listItem: Vacancy)
}

