package ru.practicum.android.diploma.ui.viewholders

import android.util.Log
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
            Log.d("IndustryViewHolder", "IS CHECked %s".format(industry.toString()))
            onItemClickListener(adapterPosition)
        }
        binding.industryRadioButton.text = industry.name
    }
}