package ru.practicum.android.diploma.data.sharing

import android.content.Context
import ru.practicum.android.diploma.R

class SharingRepositoryImpl(
    private val externalNavigator: ExternalNavigator,
    private val context: Context
) : SharingRepository {
    override fun shareVacancy(vacancyId: String) {
        externalNavigator.shareLink(context.getString(R.string.share_vacancy_url).format(vacancyId))
    }
}