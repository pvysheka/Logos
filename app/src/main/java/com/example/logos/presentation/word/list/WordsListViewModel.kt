package com.example.logos.presentation.word.list

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.logos.data.repositoty.DatabaseRepository
import com.example.logos.presentation.group.details.ARGUMENT_GROUP_ID
import com.example.logos.usecase.word.FetchAllWordsUseCase
import com.example.logos.usecase.word.FetchGroupWithWordsByIdUseCase
import com.example.logos.usecase.word.FetchWordByGroupIdUseCase
import com.example.logos.usecase.word.UpdateWordProgressUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WordsListViewModel @Inject constructor(
	savedStateHandle: SavedStateHandle,
	private val databaseRepository: DatabaseRepository,
	private val fetchAllWordsUseCase: FetchAllWordsUseCase,
	private val updateWordProgressUseCase: UpdateWordProgressUseCase,
	private val fetchGroupWithWordsByIdUseCase: FetchGroupWithWordsByIdUseCase,
	private val fetchWordByGroupIdUseCase: FetchWordByGroupIdUseCase
) : ViewModel() {

	private val _uiState = MutableStateFlow(MainWordUIState())
	val uiState get() = _uiState.asStateFlow()

	private val groupId: Long = savedStateHandle.get<Long>(ARGUMENT_GROUP_ID) ?: NO_GROUP_ID

	init {
		if (groupId.isValidGroupId()) {
			fetchWords()
		}
	}

	fun fetchWords() {
		viewModelScope.launch {
			if (groupId.isValidGroupId()) {
				fetchWordByGroupIdUseCase(groupId).collect { words ->
					_uiState.update { it.copy(words = words) }
				}
			}
		}
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