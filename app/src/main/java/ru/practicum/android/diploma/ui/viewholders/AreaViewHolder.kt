package ru.practicum.android.diploma.ui.viewholders

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.domain.models.Area

class AreaViewHolder(itemView: View) : ViewHolder(itemView) {

    val name: TextView = itemView.findViewById(R.id.area_name)

    fun bind(area: Area) {
        name.text = area.name
    }
}
