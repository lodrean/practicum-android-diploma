package ru.practicum.android.diploma.data.sharing

import android.content.Context
import android.content.Intent
import android.net.Uri
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

    fun openEmail(emailData: EmailData) {
        val intentEmail = Intent(Intent.ACTION_SENDTO).apply {
            data = Uri.parse(context.getString(R.string.mailto).format(emailData.mailTo))

            putExtra(Intent.EXTRA_SUBJECT, emailData.subject)
            putExtra(Intent.EXTRA_TEXT, emailData.text)
        }

        context.startActivity(intentEmail.apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK
        })
    }

    fun openPhone(phoneNumber: String) {
        val callIntent = Intent(Intent.ACTION_DIAL).apply {
            data = Uri.parse("tel:%s".format(phoneNumber))
        }
        context.startActivity(callIntent.apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK
        })
    }
}