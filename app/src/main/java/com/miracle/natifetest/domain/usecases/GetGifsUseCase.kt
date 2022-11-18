package com.miracle.natifetest.domain.usecases

import com.miracle.natifetest.domain.repository.GifsRepository
import com.miracle.natifetest.domain.repository.SharedPreferencesRepository
import javax.inject.Inject

class GetGifsUseCase @Inject constructor(
    private val gifsRepository: GifsRepository,
    private val sharedPreferencesRepository: SharedPreferencesRepository
) {
    suspend operator fun invoke(searchString: String, limit: Int, offset: Int): List<String> {
        val blockedGifs = sharedPreferencesRepository.getBlockedGifs()
        return gifsRepository.getGifs(searchString, limit, offset)
            .filter { !blockedGifs.contains(it) }
    }
}
