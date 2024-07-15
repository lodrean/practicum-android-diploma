package ru.practicum.android.diploma.data.sharing

import android.content.Context
import android.content.Intent
import ru.practicum.android.diploma.R

class ExternalNavigator(val context: Context) {
    fun shareLink(link: String) {
        val shareIntent: Intent = Intent().apply {
            action = Intent.ACTION_SEND
            putExtra(Intent.EXTRA_TEXT, link)
            type = context.getString(R.string.share_app_text_plain)
            flags = Intent.FLAG_ACTIVITY_NEW_TASK
        }
        context.startActivity(Intent.createChooser(shareIntent, null).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK
        })
    }
}