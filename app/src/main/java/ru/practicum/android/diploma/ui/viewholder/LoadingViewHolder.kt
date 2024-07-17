package ru.practicum.android.diploma.ui.viewholder

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.domain.models.Vacancy
import ru.practicum.android.diploma.ui.ListItemUiModel
import ru.practicum.android.diploma.util.OnItemClickListener
import ru.practicum.android.diploma.util.UtilityFunctions

class LoadingViewHolder(itemView: View) :
    ListItemViewHolder(itemView) {

    override fun bind(listitem: ListItemUiModel) {
        require(listitem is ListItemUiModel.Loading)
        { "ListItemUiModel.Loading" }
    }
}
