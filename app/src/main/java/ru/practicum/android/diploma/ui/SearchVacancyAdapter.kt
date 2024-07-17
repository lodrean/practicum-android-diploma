package ru.practicum.android.diploma.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.viewmodel.viewModelFactory
import androidx.recyclerview.widget.RecyclerView
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.domain.models.Vacancy
import ru.practicum.android.diploma.ui.viewholder.EmptyViewHolder
import ru.practicum.android.diploma.ui.viewholder.ListItemViewHolder
import ru.practicum.android.diploma.ui.viewholder.LoadingViewHolder
import ru.practicum.android.diploma.ui.viewholder.SearchVacancyViewHolder
import ru.practicum.android.diploma.ui.viewholder.VacancyViewHolder
import ru.practicum.android.diploma.util.OnItemClickListener

class SearchVacancyAdapter(private val onItemClickListener: OnItemClickListener) :
    RecyclerView.Adapter<ListItemViewHolder>() {

    private val listData = mutableListOf<ListItemUiModel>()
    private var showLoading: Boolean = false

    fun setData(newListData: List<ListItemUiModel>) {
        listData.clear()
        listData.add(0, ListItemUiModel.Empty)
        listData.addAll(newListData)
        notifyItemRangeChanged(0, listData.size, true)
    }

    fun showLoading(isShowLoading: Boolean) {
        showLoading = isShowLoading
        if (showLoading) {
            listData.add(ListItemUiModel.Loading)
            notifyItemRangeChanged(listData.size-1, 1, true)
        } else {
            if (listData.size > 0) {
                listData.removeAt(listData.size-1);
                notifyItemRangeChanged(0, listData.size)
            }

        }
    }

    override fun getItemViewType(position: Int) =

        when (listData[position]) {
            is ListItemUiModel.Loading -> VIEW_TYPE_LOADING
            is ListItemUiModel.VacancyListItem -> VIEW_TYPE_VACANCY
            ListItemUiModel.Empty -> VIEW_TYPE_EMPTY
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = when (viewType) {
        VIEW_TYPE_VACANCY -> {
            SearchVacancyViewHolder(
                LayoutInflater.from(parent.context)
                    .inflate(R.layout.vacancy_list_item, parent, false)
            ) { vacancy -> onItemClickListener.onItemClick(vacancy) }
        }

        VIEW_TYPE_EMPTY -> {
            EmptyViewHolder(
                LayoutInflater.from(parent.context)
                    .inflate(R.layout.empty_list_item, parent, false)
            )
        }


        VIEW_TYPE_LOADING -> {
            LoadingViewHolder(
                LayoutInflater.from(parent.context)
                    .inflate(R.layout.loading_list_item, parent, false)
            )

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
        return listData.size
        //return listData.size + 1 + if (showLoading) 1 else 0
    }

    override fun onBindViewHolder(holder: ListItemViewHolder, position: Int) {

        holder.bind(listData[position])

    }

    companion object {
        private const val VIEW_TYPE_EMPTY = 0
        private const val VIEW_TYPE_VACANCY = 1
        private const val VIEW_TYPE_LOADING = 2
    }

}
