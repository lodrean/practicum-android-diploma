package ru.practicum.android.diploma.data.sharing

import android.content.Context
import androidx.core.content.ContextCompat
import ru.practicum.android.diploma.R

class SharingRepositoryImpl(
    private val externalNavigator: ExternalNavigator,
    private val context: Context
): SharingRepository {
    override fun shareVacancy(vacancyId: String) {
        externalNavigator.shareLink(context.getString(R.string.share_vacancy_url).format(vacancyId))
    }

    override fun openEmail(email: String, vacancyName: String) {
        externalNavigator.openEmail(
            EmailData(
                email,
                vacancyName,
                ContextCompat.getString(context, R.string.message_email)
        )
        )
    }

    override fun callPhone(phoneNumber: String){
        externalNavigator.openPhone(phoneNumber)
    }
}