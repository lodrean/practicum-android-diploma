package ru.practicum.android.diploma.data.sharing

interface SharingRepository {
    fun shareVacancy(vacancyId: String)
    fun openEmail(email: String, vacancyName: String)
    fun callPhone(phoneNumber: String)
}