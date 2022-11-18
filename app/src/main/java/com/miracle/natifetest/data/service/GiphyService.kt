package com.miracle.natifetest.data.service

import com.miracle.natifetest.data.model.GiphyResponse
import retrofit2.http.GET
import retrofit2.http.Query

const val API_KEY = "A761SXcIMG6dNyg1gCnC1BUOC8m1wruR"

interface GiphyService {

    @GET("v1/gifs/search")
    suspend fun getPosts(
        @Query("api_key") apiKey: String,
        @Query("q") searchString: String,
        @Query("limit") limit: Int,
        @Query("offset") offset: Int,
    ): GiphyResponse
}
