package ru.practicum.android.diploma.ui

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView.Adapter
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.ui.search.VacancyListItemUiModel
import ru.practicum.android.diploma.ui.viewholder.EmptyViewHolder
import ru.practicum.android.diploma.ui.viewholder.ListItemViewHolder
import ru.practicum.android.diploma.ui.viewholder.LoadingViewHolder
import ru.practicum.android.diploma.ui.viewholder.VacancyViewHolder
import ru.practicum.android.diploma.util.OnItemClickListener

private const val VIEW_TYPE_EMPTY = 0
private const val VIEW_TYPE_VACANCY = 1
private const val VIEW_TYPE_LOADING = 2

class VacancyAdapter(private val onItemClickListener: OnItemClickListener) : Adapter<ListItemViewHolder>() {

    private val listData = mutableListOf<VacancyListItemUiModel>()

    fun setData(newListData: List<VacancyListItemUiModel>) {
        listData.clear()
        listData.addAll(newListData)
        notifyDataSetChanged()
    }

    override fun getItemViewType(position: Int) =
        when (listData[position]) {
            VacancyListItemUiModel.Empty -> VIEW_TYPE_EMPTY
            VacancyListItemUiModel.Loading -> VIEW_TYPE_LOADING
            is VacancyListItemUiModel.VacancyItem -> VIEW_TYPE_VACANCY
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = when (viewType) {
        VIEW_TYPE_VACANCY -> {
            VacancyViewHolder(
                LayoutInflater.from(parent.context).inflate(R.layout.vacancy_list_item, parent, false)
            ) { vacancy -> onItemClickListener.onItemClick(vacancy) }
        }

        VIEW_TYPE_EMPTY -> {
            EmptyViewHolder(
                LayoutInflater.from(parent.context).inflate(R.layout.empty_list_item, parent, false)
            )
        }

        VIEW_TYPE_LOADING -> {
            LoadingViewHolder(
                LayoutInflater.from(parent.context).inflate(R.layout.loading_list_item, parent, false)
            )
        }

        else -> {
            throw IllegalArgumentException("Unknown view type requested: $viewType")
        }
    }

    override fun getItemCount(): Int {
        return listData.size
    }

    override fun onBindViewHolder(holder: ListItemViewHolder, position: Int) {
        holder.bind(listData[position])
    }

}
