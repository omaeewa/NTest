package com.miracle.natifetest.presentation.screens.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.miracle.natifetest.domain.repository.UserDataRepository
import com.miracle.natifetest.domain.usecases.GifSearchUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class HomeViewModel @Inject constructor(
    private val gifSearchUseCase: GifSearchUseCase,
) : ViewModel() {

    private val _listViewType = MutableStateFlow(ListViewType.Table)
    val listViewType get() = _listViewType.asStateFlow()

    val gifUrls = gifSearchUseCase.gifs
    val searchString = gifSearchUseCase.queryString
    val isLoading = gifSearchUseCase.isLoading

    fun onSearchStringUpdate(updatedString: String) {
        viewModelScope.launch {
            gifSearchUseCase.onQueryChanged(updatedString)
        }
    }


    fun loadMoreGifs() {
        viewModelScope.launch {
            gifSearchUseCase.loadMore()
        }
    }

    fun updateListViewType() {
        val newType =
            if (_listViewType.value == ListViewType.Table) ListViewType.Column else ListViewType.Table
        _listViewType.update { newType }
    }

}




enum class ListViewType {
    Table, Column
}