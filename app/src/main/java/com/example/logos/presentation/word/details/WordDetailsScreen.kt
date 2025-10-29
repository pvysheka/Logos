package com.example.logos.presentation.word.details

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.example.logos.presentation.navigation.NavRoute
import com.example.logos.ui.BaseScreenBox

const val ARGUMENT_WORD_ID = "ARGUMENT_WORD_ID"

fun NavGraphBuilder.wordDetailsScreen(
	navController: NavController
) {
	composable(route = NavRoute.WORD_DETAILS.name) {
		val viewModel: WordDetailsViewModel = hiltViewModel()
		val uiState by viewModel.uiState.collectAsState()

		WordDetailsScreen(
			uiState = uiState
		)
	}
}

@Composable
fun WordDetailsScreen(
	uiState: WordDetailsUIState
) {
	BaseScreenBox(
		modifier = Modifier
			.fillMaxSize()
			.padding(top = 64.dp)
	) {
		Column(
			modifier = Modifier.fillMaxSize(),
			horizontalAlignment = Alignment.CenterHorizontally
		) {
			Text(
				text = "Details Screen\n${uiState.word?.word}",
				textAlign = TextAlign.Center,
				modifier = Modifier
					.padding(8.dp)
					.fillMaxWidth()
			)
		}
	}
}

@Preview
@Composable
fun WordDetailsScreenPreview() {
	WordDetailsScreen(
		uiState = WordDetailsUIState()
	)
}