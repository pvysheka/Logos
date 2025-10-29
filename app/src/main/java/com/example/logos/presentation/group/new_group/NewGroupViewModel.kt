package com.example.logos.presentation.group.new_group

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.logos.usecase.group.CreateGroupUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NewGroupViewModel @Inject constructor(
	private val createGroupUseCase: CreateGroupUseCase
) : ViewModel() {

	fun saveGroup(title: String) {
		viewModelScope.launch {
			createGroupUseCase(title)
		}
	}
}