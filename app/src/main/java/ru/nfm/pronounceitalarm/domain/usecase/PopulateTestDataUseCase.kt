package ru.nfm.pronounceitalarm.domain.usecase

import ru.nfm.pronounceitalarm.domain.repository.AlarmRepository
import javax.inject.Inject

//TODO: ONLY FOR TESTING DELETE FOR PROD
class PopulateTestDataUseCase @Inject constructor(
    private val repository: AlarmRepository
){

    suspend operator fun invoke() {
        repository.populateTestData()
    }
}