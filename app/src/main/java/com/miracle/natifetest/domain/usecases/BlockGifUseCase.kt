package com.miracle.natifetest.domain.usecases

import com.miracle.natifetest.domain.repository.GifsRepository
import com.miracle.natifetest.domain.repository.SharedPreferencesRepository
import javax.inject.Inject

class BlockGifUseCase @Inject constructor(
    private val sharedPreferencesRepository: SharedPreferencesRepository
) {
    operator fun invoke(gifUrl: String) {
        sharedPreferencesRepository.addBlockedGif(gifUrl)
    }
}
