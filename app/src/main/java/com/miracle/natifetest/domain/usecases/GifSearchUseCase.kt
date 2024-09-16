package com.miracle.natifetest.domain.usecases

import android.util.Log
import com.miracle.natifetest.domain.repository.GifsRepository
import com.miracle.natifetest.domain.repository.UserDataRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.scan
import kotlinx.coroutines.flow.update
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GifSearchUseCase @Inject constructor(
    private val gifsRepository: GifsRepository,
    userDataRepository: UserDataRepository
) {
    private val hiddenGifs = userDataRepository.userData.map { it.hiddenGifs }
    private val offset = MutableStateFlow(0)

    private val _queryString = MutableStateFlow("")
    val queryString: StateFlow<String> = _queryString

    private val _gifs = MutableStateFlow(emptyList<String>())
    val gifs = combine(_gifs, hiddenGifs) { gif, hidden ->
        gif.filterNot(hidden::contains)
    }

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    suspend fun onQueryChanged(newQuery: String) {
        offset.update { 0 }
        _queryString.update { newQuery }
        _gifs.update { receiveGifs() }
    }

    suspend fun loadMore() {
        offset.update { it + LIMIT_GIFS }
        _gifs.update { it + receiveGifs() }
    }

    private suspend fun receiveGifs(): List<String> {
        _isLoading.update { true }
        val gifs = gifsRepository.getGifs(_queryString.value, LIMIT_GIFS, offset.value)
        _isLoading.update { false }
        return gifs
    }


    companion object {
        const val LIMIT_GIFS = 25
    }
}
