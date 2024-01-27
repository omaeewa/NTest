package com.miracle.natifetest.domain.usecases

import com.miracle.natifetest.domain.repository.GifsRepository
import com.miracle.natifetest.domain.repository.UserDataRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GifSearchUseCase @Inject constructor(
    private val gifsRepository: GifsRepository,
    private val userDataRepository: UserDataRepository
) {
    suspend operator fun invoke(searchString: String, limit: Int, offset: Int) = try {
        val hiddenGifs = userDataRepository.userData.map { it.hiddenGifs }.first()
        val receivedGifs = gifsRepository.getGifs(searchString, limit, offset).getOrThrow()

        val filteredGifs = receivedGifs.filter { !hiddenGifs.contains(it) }
        Result.success(filteredGifs)
    } catch (e: Exception) {
        Result.failure(e)
    }
}
