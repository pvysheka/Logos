package com.example.logos.presentation.dashboard

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.logos.data.repositoty.DatabaseRepository
import com.example.logos.usecase.FetchAllWordsUseCase
import com.example.logos.usecase.UpdateWordProgressUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DashboardViewModel @Inject constructor(
	private val databaseRepository: DatabaseRepository,
	private val fetchAllWordsUseCase: FetchAllWordsUseCase,
	private val updateWordProgressUseCase: UpdateWordProgressUseCase,
) : ViewModel() {

	private val _uiState = MutableStateFlow(MainWordUIState())
	val uiState get() = _uiState.asStateFlow()

	init {
		fetchRandomWord()
	}

	fun fetchRandomWord() {
		viewModelScope.launch {
			//val word: WordEntity = databaseRepository.getRandomWordId(uiState.value.word)
			fetchAllWordsUseCase().collect { words ->
				_uiState.update { it.copy(words = words) }
			}
		}
	}

	fun increaseCorrectCount() {
		updateWrongCount(1)
	}

	fun increaseWrongCount() = updateWrongCount(-1)

	private fun updateWrongCount(value: Int) {
		viewModelScope.launch {
			uiState.value.words.firstOrNull()?.let { updateWordProgressUseCase(it, value) }
		}
	}
}