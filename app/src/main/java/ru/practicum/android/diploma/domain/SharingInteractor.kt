package ru.practicum.android.diploma.domain

interface SharingInteractor {
    fun shareVacancy(vacancyId: String)
    fun openEmail(email: String, vacancyName: String)
    fun callPhone(phoneNumber: String)
}