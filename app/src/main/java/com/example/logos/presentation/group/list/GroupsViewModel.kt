package com.example.logos.presentation.group.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.logos.data.repositoty.DatabaseRepository
import com.example.logos.usecase.group.FetchAllGroupsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GroupsViewModel @Inject constructor(
	private val databaseRepository: DatabaseRepository,
	private val fetchAllGroupsUseCase: FetchAllGroupsUseCase
) : ViewModel() {

	private val _uiState = MutableStateFlow(GroupsUIState())
	val uiState get() = _uiState.asStateFlow()

	init {
		fetchGroups()
	}

	fun fetchGroups() {
		viewModelScope.launch {
			fetchAllGroupsUseCase().collect { groups ->
				_uiState.update { it.copy(groups = groups) }
			}
		}
	}
}