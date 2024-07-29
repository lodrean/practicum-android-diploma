package ru.practicum.android.diploma.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView.Adapter
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.domain.models.Area
import ru.practicum.android.diploma.ui.viewholders.AreaViewHolder
import ru.practicum.android.diploma.util.OnAreaItemClickListener

class AreaAdapter(private val onAreaItemClickListener: OnAreaItemClickListener) : Adapter<AreaViewHolder>() {

    private val listOfAreas: MutableList<Area> = mutableListOf()

    fun setItems(items: List<Area>) {
        listOfAreas.clear()
        listOfAreas.addAll(items)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AreaViewHolder = AreaViewHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.area_item, parent, false)
    )

    override fun getItemCount(): Int {
        return listOfAreas.size
    }

    override fun onBindViewHolder(holder: AreaViewHolder, position: Int) {
        val currentArea = listOfAreas[position]
        holder.bind(currentArea)
        holder.itemView.setOnClickListener {
            onAreaItemClickListener.onClick(currentArea)
        }
    }
}
