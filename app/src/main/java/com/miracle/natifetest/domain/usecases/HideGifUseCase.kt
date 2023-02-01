package com.miracle.natifetest.domain.usecases

import com.miracle.natifetest.domain.repository.SharedPreferencesRepository
import javax.inject.Inject

class HideGifUseCase @Inject constructor(
    private val sharedPreferencesRepository: SharedPreferencesRepository
) {
    operator fun invoke(gifUrl: String) {
        sharedPreferencesRepository.addDisabledGif(gifUrl)
    }
}
