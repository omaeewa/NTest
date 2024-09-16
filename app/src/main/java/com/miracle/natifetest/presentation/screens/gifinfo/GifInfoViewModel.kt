package com.miracle.natifetest.presentation.screens.gifinfo

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.miracle.natifetest.domain.repository.UserDataRepository
import com.miracle.natifetest.domain.usecases.GifSearchUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GifInfoViewModel @Inject constructor(
    private val userDataRepository: UserDataRepository,
    private val gifSearchUseCase: GifSearchUseCase,
    ): ViewModel() {
    val gifUrls = gifSearchUseCase.gifs


    fun moveGitToHidden(gifUrl: String) {
        viewModelScope.launch {
            userDataRepository.addHiddenGif(gifUrl)
        }
    }
}