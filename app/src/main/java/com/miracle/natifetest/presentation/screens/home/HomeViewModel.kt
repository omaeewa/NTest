package com.miracle.natifetest.presentation.screens.home

import android.util.Log
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.miracle.natifetest.domain.repository.UserDataRepository
import com.miracle.natifetest.domain.usecases.GifSearchUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject


data class HomeUiState(
    val searchString: String = "",
    val gifUrls: List<String> = emptyList(),
    val loading: Boolean = false,
    val listViewType: ListViewType = ListViewType.Table
)

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val gifSearchUseCase: GifSearchUseCase,
    private val userDataRepository: UserDataRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(HomeUiState())
    val uiState get() = _uiState.asStateFlow()
    private var searchJob: Job? = null

    private val offset get() = uiState.value.gifUrls.size

    fun onSearchStringUpdate(updatedString: String) {
        _uiState.update { it.copy(searchString = updatedString) }
        fetchGifs()
    }

    fun fetchGifs() {
        searchJob?.cancel()
        searchJob = viewModelScope.launch {
            delay(300)
            try {
                updateLoadingState(true)
                val receivedGifs = gifSearchUseCase(_uiState.value.searchString, LIMIT_GIFS, offset).getOrThrow()
                _uiState.update { it.copy(gifUrls = receivedGifs) }
            } catch (e: Exception) {
                //Handle exception
                Log.d("kek", e.message.toString())
            } finally {
                updateLoadingState(false)
                Log.d("kek", "size -> $offset")
            }
        }
    }

    fun loadMoreGifs() {
        viewModelScope.launch {
            try {
                val receivedGifs = gifSearchUseCase(_uiState.value.searchString, LIMIT_GIFS, offset).getOrThrow()
                _uiState.update { it.copy(gifUrls = it.gifUrls + receivedGifs) }
            } catch (e: Exception) {
                //Handle exception
                Log.d("kek", e.message.toString())
            }
        }

    }


    private fun updateLoadingState(loading: Boolean) {
        _uiState.update { it.copy(loading = loading) }
    }

    fun updateListViewType() {
        val newType =
            if (uiState.value.listViewType == ListViewType.Table) ListViewType.Column else ListViewType.Table
        _uiState.update { it.copy(listViewType = newType) }
    }

    fun blockGif(gifUrl: String) {
        viewModelScope.launch {
            userDataRepository.addHiddenGif(gifUrl)
        }
        _uiState.update { it.copy(gifUrls = it.gifUrls.toMutableList().apply { remove(gifUrl) }) }

    }


}


const val LIMIT_GIFS = 25


enum class ListViewType {
    Table, Column
}