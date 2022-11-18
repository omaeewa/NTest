package com.miracle.natifetest.domain.repository

interface SharedPreferencesRepository {
    fun addBlockedGif(gifUrl: String)
    fun getBlockedGifs(): List<String>
}