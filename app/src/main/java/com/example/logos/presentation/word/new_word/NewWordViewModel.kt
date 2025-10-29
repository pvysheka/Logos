package com.example.logos.presentation.word.new_word

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.logos.usecase.word.SaveWordUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NewWordViewModel @Inject constructor(
	private val saveWordUseCase: SaveWordUseCase
) : ViewModel() {

	fun saveWord(newWOrd: String, meaning: String) {
		viewModelScope.launch {
			saveWordUseCase(newWOrd = newWOrd, meaning = meaning)
		}
	}
}