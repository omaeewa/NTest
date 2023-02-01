package com.miracle.natifetest.data.repository

import android.content.SharedPreferences
import com.miracle.natifetest.domain.repository.SharedPreferencesRepository
import javax.inject.Inject

class SharedPreferencesRepositoryImpl @Inject constructor(
    private val sharedPref: SharedPreferences,
    ): SharedPreferencesRepository {

    override fun addDisabledGif(gifUrl: String) {
        val blockedStrings = sharedPref.getStringSet(BLOCKED_GIFS, emptySet())!!.toMutableSet()
        blockedStrings.add(gifUrl)

        with(sharedPref.edit()) {
            putStringSet(BLOCKED_GIFS, blockedStrings)
            apply()
        }
    }

    override fun getDisabledGifs(): List<String> = sharedPref.getStringSet(BLOCKED_GIFS, emptySet())!!.toList()

}

const val BLOCKED_GIFS = "blocked_gifs"
