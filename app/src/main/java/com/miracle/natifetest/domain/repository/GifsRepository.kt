package com.miracle.natifetest.domain.repository

import kotlinx.coroutines.flow.Flow

interface GifsRepository {

    suspend fun getGifs(searchString: String, limit: Int, offset: Int): Result<List<String>>

}