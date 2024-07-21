package ru.practicum.android.diploma.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ru.practicum.android.diploma.databinding.IndustryListItemBinding
import ru.practicum.android.diploma.domain.models.Industry
import ru.practicum.android.diploma.ui.viewholders.IndustryViewHolder
import java.lang.Exception

class IndustryAdapter(private val onItemClickListener: (Industry) -> Unit) :
    RecyclerView.Adapter<IndustryViewHolder>() {

    val listData = mutableListOf<Industry>()
    private var selectedPosition: Int = -1

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = IndustryViewHolder(
        IndustryListItemBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
    ) {
        selectedPosition = -1
        onItemClickListener(listData[it])
    }


    override fun onBindViewHolder(holder: IndustryViewHolder, position: Int) {
        holder.binding.industryRadioButton.isChecked = position == selectedPosition

        holder.binding.industryRadioButton.setOnCheckedChangeListener { compoundButton, b ->
            if (b) {
                try {
                    notifyItemChanged(selectedPosition)
                } catch (e: Exception) {

                }
                selectedPosition = holder.adapterPosition
                onItemClickListener(listData[selectedPosition])
            }
        }

        listData.getOrNull(position)?.let {
            holder.bind(listData[position])
        }
    }

    override fun getItemCount(): Int = listData.size
}