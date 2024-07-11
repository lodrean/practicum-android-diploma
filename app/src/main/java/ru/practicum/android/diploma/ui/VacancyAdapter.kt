package ru.practicum.android.diploma.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView.Adapter
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.domain.models.Vacancy

class VacancyAdapter(val listOfVacancies: List<Vacancy>, private val callback: Callback) :
    Adapter<VacancyViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VacancyViewHolder {
        return VacancyViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.vacancy_list_item, parent, false))
    }

    override fun getItemCount(): Int {
        return listOfVacancies.size
    }

    override fun onBindViewHolder(holder: VacancyViewHolder, position: Int) {
        holder.bind(listOfVacancies[position])

        holder.itemView.setOnClickListener {
            callback.onClick(listOfVacancies[position])
        }
    }

    fun interface Callback {
        fun onClick(vacancy: Vacancy)

    }

}
