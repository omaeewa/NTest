package com.miracle.natifetest.domain.repository

import com.miracle.natifetest.domain.model.UserData
import kotlinx.coroutines.flow.Flow

interface UserDataRepository {
    val userData: Flow<UserData>

    suspend fun addHiddenGif(gifUrl: String)
}