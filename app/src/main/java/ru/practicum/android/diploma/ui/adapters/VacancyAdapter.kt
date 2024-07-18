package ru.practicum.android.diploma.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.domain.models.Vacancy
import ru.practicum.android.diploma.ui.viewholder.VacancyViewHolder
import ru.practicum.android.diploma.util.OnItemClickListener

class VacancyAdapter(private val onItemClickListener: OnItemClickListener) :
    RecyclerView.Adapter<VacancyViewHolder>() {

    val listData = mutableListOf<Vacancy>()

    fun setData(newListData: List<Vacancy>) {
        listData.clear()
        listData.addAll(newListData)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = VacancyViewHolder(
        LayoutInflater.from(parent.context)
            .inflate(R.layout.vacancy_list_item, parent, false)
    ) { vacancy -> onItemClickListener.onItemClick(vacancy) }

    override fun getItemCount(): Int = listData.size

    override fun onBindViewHolder(holder: VacancyViewHolder, position: Int) {
        holder.bind(listData[position])
    }

}
