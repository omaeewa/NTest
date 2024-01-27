package com.miracle.natifetest.data.repository

import com.miracle.natifetest.data.service.GiphyService
import com.miracle.natifetest.domain.repository.GifsRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.withContext
import javax.inject.Inject

class GifsRepositoryImpl @Inject constructor(
    private val giphyService: GiphyService,
    private val dispatcherIo: CoroutineDispatcher = Dispatchers.IO
) : GifsRepository {

    override suspend fun getGifs(searchString: String, limit: Int, offset: Int) = withContext(dispatcherIo) {
        try {
            val gifs = giphyService.getPosts(searchString, limit, offset).data.map { it.images.fixed_height.url }
            Result.success(gifs)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}