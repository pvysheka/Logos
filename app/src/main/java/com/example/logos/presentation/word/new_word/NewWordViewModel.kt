package com.example.logos.presentation.word.new_word

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.logos.data.storage.entity.GroupEntity
import com.example.logos.usecase.group.FetchAllGroupsUseCase
import com.example.logos.usecase.word.SaveWordUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NewWordViewModel @Inject constructor(
	private val saveWordUseCase: SaveWordUseCase,
	private val fetchAllGroupsUseCase: FetchAllGroupsUseCase,
) : ViewModel() {

	private val _uiState = MutableStateFlow(NewWordUiState())
	val uiState get() = _uiState.asStateFlow()

	private var preselectedGroupId: Long? = null

	init {
		loadGroups()
	}

	fun saveWord(newWord: String, meaning: String) {
		viewModelScope.launch {
			saveWordUseCase(
				newWord = newWord,
				meaning = meaning,
				groupId = uiState.value.selectedGroup?.id
			)
		}
	}

	fun selectGroup(groupId: Long) {
		val selected = uiState.value.groups.firstOrNull { it.id == groupId }
		_uiState.update { state -> state.copy(selectedGroup = selected) }
	}

	fun setPreselectedGroup(groupId: Long?) {
		preselectedGroupId = groupId
		// If we already have groups loaded, apply immediately
		if (groupId != null && uiState.value.groups.isNotEmpty()) {
			selectGroup(groupId)
		}
	}

	private fun loadGroups() {
		viewModelScope.launch {
			fetchAllGroupsUseCase().collect { groups ->
				_uiState.update { state ->
					val currentSelection = state.selectedGroup
						?: preselectedGroupId?.let { id -> groups.firstOrNull { it.id == id } }
					state.copy(
						groups = groups,
						selectedGroup = currentSelection ?: groups.firstOrNull()
					)
				}
			}
		}
	}
}

data class NewWordUiState(
	val groups: List<GroupEntity> = emptyList(),
	val selectedGroup: GroupEntity? = null
)