package ru.practicum.android.diploma.ui.viewholders

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.radiobutton.MaterialRadioButton
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.domain.models.Industry

class IndustryViewHolder(
    parent: ViewGroup,
    private val onItemClickListener: (Industry) -> Unit,
    private val onRadioClickListener: (Industry) -> Unit,
) : RecyclerView.ViewHolder(
    LayoutInflater
        .from(parent.context)
        .inflate(R.layout.industry_list_item, parent, false)
) {
    private val industryRadioButton = itemView.findViewById<MaterialRadioButton>(R.id.industry_radio_button)

    fun bind(industry: Industry, isChecked: Boolean) {
        itemView.setOnClickListener { onItemClickListener(industry) }
        industryRadioButton.setOnCheckedChangeListener(null)
        industryRadioButton.isChecked = isChecked
        industryRadioButton.setOnCheckedChangeListener { _, isCheched ->
            if (isCheched) {
                onRadioClickListener(industry)
            }
        }
        industryRadioButton.text = industry.name
    }
}
