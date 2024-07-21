package ru.practicum.android.diploma.ui.viewholders

import androidx.recyclerview.widget.RecyclerView
import ru.practicum.android.diploma.databinding.IndustryListItemBinding
import ru.practicum.android.diploma.domain.models.Industry

class IndustryViewHolder(
    val binding: IndustryListItemBinding,
    private val onItemClickListener: (position: Int) -> Unit
) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(industry: Industry) {
        itemView.setOnClickListener {
            onItemClickListener(adapterPosition)
        }
        binding.industryRadioButton.text = industry.name
        binding.industryRadioButton.isChecked = false
    }
}