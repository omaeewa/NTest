package com.miracle.natifetest.data.repository

import android.content.SharedPreferences
import com.miracle.natifetest.domain.repository.SharedPreferencesRepository
import javax.inject.Inject

class SharedPreferencesRepositoryImpl @Inject constructor(
    private val sharedPref: SharedPreferences,
    ): SharedPreferencesRepository {

    override fun addBlockedGif(gifUrl: String) {
        val blockedStrings = sharedPref.getStringSet(BLOCKED_GIFS, emptySet())!!.toMutableSet()
        blockedStrings.add(gifUrl)

        with(sharedPref.edit()) {
            putStringSet(BLOCKED_GIFS, blockedStrings)
            apply()
        }
    }

    override fun getBlockedGifs(): List<String> = sharedPref.getStringSet(BLOCKED_GIFS, emptySet())!!.toList()

    private val BLOCKED_GIFS = "blocked_gifs"
}