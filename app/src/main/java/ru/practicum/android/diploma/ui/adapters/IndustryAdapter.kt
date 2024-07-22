package ru.practicum.android.diploma.ui.adapters

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ru.practicum.android.diploma.domain.models.Industry
import ru.practicum.android.diploma.ui.viewholders.IndustryViewHolder

class IndustryAdapter(private val onItemClickListener: (Industry?) -> Unit) :
    RecyclerView.Adapter<IndustryViewHolder>() {

    private val listData = mutableListOf<Industry>()
    private var selectedIndustry: Industry? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): IndustryViewHolder =
        IndustryViewHolder(parent, onItemClickListener) { newIndustry ->
            val previousPosition: Int = selectedIndustry?.let { oldIndustry ->
                listData.indexOf(oldIndustry)
            } ?: -1
            selectedIndustry = newIndustry
            if (previousPosition >= 0) {
                notifyItemChanged(previousPosition)
            }
            onItemClickListener(selectedIndustry)
        }

    override fun onBindViewHolder(holder: IndustryViewHolder, position: Int) {
        holder.bind(listData[position], listData[position] == selectedIndustry)
    }

    override fun getItemCount(): Int = listData.size

    fun setData(industryList: List<Industry>) {
        listData.clear()
        listData.addAll(industryList)
        if (!listData.contains(selectedIndustry)) {
            selectedIndustry = null
        }
        onItemClickListener(selectedIndustry)
        notifyDataSetChanged()
    }

    fun getSelectedIndustry() = selectedIndustry

}
