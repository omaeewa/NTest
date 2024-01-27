package com.miracle.natifetest.data.repository

import androidx.datastore.core.DataStore
import com.miracle.natifetest.UserPreferencesProto
import com.miracle.natifetest.domain.model.UserData
import com.miracle.natifetest.domain.repository.UserDataRepository
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class UserDataRepositoryImpl @Inject constructor(
    private val userPreferences: DataStore<UserPreferencesProto>
) : UserDataRepository {
    override val userData = userPreferences.data.map {
        UserData(hiddenGifs = it.hiddenGifsList)
    }

    override suspend fun addHiddenGif(gifUrl: String) = updatePreferences {
        addHiddenGifs(gifUrl)
    }

    private suspend fun updatePreferences(updateFunction: UserPreferencesProto.Builder.() -> UserPreferencesProto.Builder) {
        try {
            userPreferences.updateData {
                it.toBuilder().updateFunction().build()
            }
        } catch (e: Exception) {
//            e.message?.let { log(it) }
        }
    }
}