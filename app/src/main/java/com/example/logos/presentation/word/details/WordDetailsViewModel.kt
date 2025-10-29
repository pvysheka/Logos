package com.example.logos.presentation.word.details

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.logos.usecase.word.FetchWordByIdUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WordDetailsViewModel @Inject constructor(
	private val fetchWordByIdUseCase: FetchWordByIdUseCase,
	private val savedStateHandle: SavedStateHandle
) : ViewModel() {

	private val _uiState = MutableStateFlow(WordDetailsUIState())
	val uiState get() = _uiState.asStateFlow()

	private val wordId = savedStateHandle.get<Long>(ARGUMENT_WORD_ID)!!

	init {
		fetchWord()
	}

	private fun fetchWord() {
		viewModelScope.launch {
			fetchWordByIdUseCase(wordId).collect { word ->
				_uiState.update { it.copy(word = word) }
			}
		}
	}
}