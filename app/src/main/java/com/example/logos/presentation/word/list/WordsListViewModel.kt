package com.example.logos.presentation.word.list

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.logos.presentation.group.details.ARGUMENT_GROUP_ID
import com.example.logos.usecase.word.FetchAllWordsUseCase
import com.example.logos.usecase.word.FetchWordByGroupIdUseCase
import com.example.logos.usecase.word.UpdateWordProgressUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WordsListViewModel @Inject constructor(
	savedStateHandle: SavedStateHandle,
	private val fetchAllWordsUseCase: FetchAllWordsUseCase,
	private val updateWordProgressUseCase: UpdateWordProgressUseCase,
	private val fetchWordByGroupIdUseCase: FetchWordByGroupIdUseCase
) : ViewModel() {

	private val _uiState = MutableStateFlow(MainWordUIState())
	val uiState get() = _uiState.asStateFlow()

	private val groupId: Long = savedStateHandle.get<Long>(ARGUMENT_GROUP_ID) ?: NO_GROUP_ID

	private var wordsJob: Job? = null

	init {
		if (groupId.isValidGroupId()) {
			observeWords()
		}
	}

	fun rotateWords() {
		_uiState.update { state ->
			val words = state.words
			if (words.size <= 1) return@update state
			state.copy(words = words.drop(1) + words.first())
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

	private fun observeWords() {
		wordsJob?.cancel()
		wordsJob = viewModelScope.launch {
			val flow = if (groupId.isValidGroupId()) {
				fetchWordByGroupIdUseCase(groupId)
			} else {
				fetchAllWordsUseCase()
			}
			flow.collect { words ->
				_uiState.update { it.copy(words = words) }
			}
		}
	}
}