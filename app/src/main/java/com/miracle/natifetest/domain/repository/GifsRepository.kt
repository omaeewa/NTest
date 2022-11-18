package com.miracle.natifetest.domain.repository

interface GifsRepository {

    suspend fun getGifs(searchString: String, limit: Int, offset: Int): List<String>

}