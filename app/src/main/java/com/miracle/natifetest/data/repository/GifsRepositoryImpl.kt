package com.miracle.natifetest.data.repository

import com.miracle.natifetest.data.DispatcherProvider
import com.miracle.natifetest.data.service.GiphyService
import com.miracle.natifetest.domain.repository.GifsRepository
import kotlinx.coroutines.withContext
import javax.inject.Inject

class GifsRepositoryImpl @Inject constructor(
    private val giphyService: GiphyService,
    private val dispatcherProvider: DispatcherProvider
) : GifsRepository {

    override suspend fun getGifs(searchString: String, limit: Int, offset: Int) =
        withContext(dispatcherProvider.io) {
            val gifs = giphyService.getPosts(
                searchString = searchString,
                limit = limit,
                offset = offset
            ).data.map { it.images.fixed_height.url }
            gifs
        }
}