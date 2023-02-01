package com.miracle.natifetest.domain.repository

interface SharedPreferencesRepository {
    fun addDisabledGif(gifUrl: String)
    fun getDisabledGifs(): List<String>
}