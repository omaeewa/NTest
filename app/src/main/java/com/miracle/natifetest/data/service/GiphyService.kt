package com.miracle.natifetest.data.service

import com.miracle.natifetest.data.model.GiphyResponse
import retrofit2.http.GET
import retrofit2.http.Query


interface GiphyService {

    @GET("search")
    suspend fun getPosts(
        @Query("q") searchString: String,
        @Query("limit") limit: Int,
        @Query("offset") offset: Int,
    ): GiphyResponse
}
