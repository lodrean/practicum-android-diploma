package ru.practicum.android.diploma.domain.impl

import ru.practicum.android.diploma.data.sharing.SharingRepository
import ru.practicum.android.diploma.domain.SharingInteractor

class SharingInteractorImpl(private val sharingRepository: SharingRepository) : SharingInteractor {
    override fun shareVacancy(vacancyId: String) {
        sharingRepository.shareVacancy(vacancyId)
    }
}