package ru.practicum.android.diploma.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.domain.models.Vacancy
import ru.practicum.android.diploma.ui.viewholder.ListItemViewHolder
import ru.practicum.android.diploma.ui.viewholder.VacancyViewHolder
import ru.practicum.android.diploma.util.OnItemClickListener

class SearchVacancyAdapter(private val onItemClickListener: OnItemClickListener) :
    RecyclerView.Adapter<ListItemViewHolder>() {

    private val listData = mutableListOf<Vacancy>()
    private var showLoading: Boolean = false

    fun setData(newListData: List<Vacancy>) {
        listData.clear()
        listData.addAll(newListData)
        notifyDataSetChanged()
    }

    fun showLoading(isShowLoading: Boolean) {
        showLoading = isShowLoading
        notifyItemChanged(listData.size + 1, true)
    }

    override fun getItemViewType(position: Int) =
        when (position) {
            0 -> VIEW_TYPE_EMPTY
            listData.size + 1 -> VIEW_TYPE_LOADING
            else -> VIEW_TYPE_VACANCY
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = when (viewType) {
        VIEW_TYPE_VACANCY -> {
            VacancyViewHolder(
                LayoutInflater.from(parent.context)
                    .inflate(R.layout.vacancy_list_item, parent, false)
            ) { vacancy -> onItemClickListener.onItemClick(vacancy) }
        }

        VIEW_TYPE_EMPTY -> {
            object : ListItemViewHolder(
                LayoutInflater.from(parent.context)
                    .inflate(R.layout.empty_list_item, parent, false)
            ) {
                override fun bind(listItem: Vacancy) {
                    return
                }
            }
        }

        VIEW_TYPE_LOADING -> {
            object : ListItemViewHolder(
                LayoutInflater.from(parent.context)
                    .inflate(R.layout.loading_list_item, parent, false)
            ) {
                override fun bind(listItem: Vacancy) {
                    return
                }
            }
        }

        else -> {
            throw IllegalArgumentException(
                parent.context.getString(
                    R.string.unknown_view_type_requested,
                    viewType.toString()
                )
            )
        }
    }

    override fun getItemCount(): Int {
        return listData.size + 1 + if (showLoading) 1 else 0
    }

    override fun onBindViewHolder(holder: ListItemViewHolder, position: Int) {
        if (position > 0 && position <= listData.size) {
            holder.bind(listData[position - 1])
        }
    }

    companion object {
        private const val VIEW_TYPE_EMPTY = 0
        private const val VIEW_TYPE_VACANCY = 1
        private const val VIEW_TYPE_LOADING = 2
    }

}
