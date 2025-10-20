package com.example.logos.presentation.dashboard

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.os.bundleOf
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.example.logos.presentation.details.ARGUMENT_WORD_ID
import com.example.logos.presentation.navigation.NavRoute
import com.example.logos.presentation.navigation.navigateToDetails
import com.example.logos.ui.BaseScreenBox
import com.example.logos.ui.CardList
import com.example.logos.ui.card.SimpleWordCard
import com.example.logos.ui.card.WordCard

fun NavGraphBuilder.dashboardScreen(
	navController: NavController
) {
	composable(route = NavRoute.DASHBOARD.name) {
		val viewModel: DashboardViewModel = hiltViewModel()
		val uiState by viewModel.uiState.collectAsState()

		DashboardScreen(
			uiState = uiState,
			nextWordAction = { viewModel.fetchRandomWord() },
			navigateToDetailsAction = { navController.navigateToDetails(bundleOf(ARGUMENT_WORD_ID to uiState.words.first().id)) },
			correctClickAction = { viewModel.increaseCorrectCount() },
			wrongClickAction = { viewModel.increaseWrongCount() }
		)
	}
}

@Composable
fun DashboardScreen(
	uiState: MainWordUIState,
	nextWordAction: () -> Unit,
	navigateToDetailsAction: () -> Unit,
	correctClickAction: () -> Unit = {},
	wrongClickAction: () -> Unit = {},
) {
	val topCard = uiState.words.firstOrNull()

	BaseScreenBox(
		modifier = Modifier
			.fillMaxSize()
			.padding(top = 64.dp)
	) {
		Column(
			modifier = Modifier.fillMaxSize(),
			horizontalAlignment = Alignment.CenterHorizontally
		) {
			LazyColumn{
				itemsIndexed(uiState.words, key = { _, word -> word.id }) { index, word ->
					val isTopCard = index == 0
					AnimatedVisibility(
						visible = true,
						enter = slideInVertically(initialOffsetY = { it }) + fadeIn(),
						exit = slideOutVertically(targetOffsetY = { -it }) + fadeOut()
					) {
						if (isTopCard) {
							WordCard(
								topCard,
								modifier = Modifier
									.padding(8.dp)
									.height(80.dp),
								detailsClickAction = navigateToDetailsAction,
								correctClickAction = {
									correctClickAction()
									nextWordAction()
								},
								wrongClickAction = {
									wrongClickAction()
									nextWordAction()
								},
							)
						} else {
							SimpleWordCard(word = word)
						}
					}
				}
			}
			/*			WordCard(
				uiState.word,
				modifier = Modifier
					.padding(24.dp),
				detailsClickAction = navigateToDetailsAction,
				correctClickAction = {
					correctClickAction()
					nextWordAction()
				},
				wrongClickAction = {
					wrongClickAction()
					nextWordAction()
				},
			)

			NextWordButton {}*/
			/*CardList(
				words = uiState.words,
				correctClickAction = correctClickAction,
				wrongClickAction = wrongClickAction
			)*/
		}
	}


}

@Composable
fun NextWordButton(
	modifier: Modifier = Modifier,
	enabled: Boolean = true,
	clickAction: () -> Unit
) {
	Button(
		modifier = modifier
			.fillMaxWidth()
			.padding(24.dp),
		enabled = enabled,
		onClick = clickAction
	) {
		Text(
			text = "Next",
		)
	}
}

@Preview
@Composable
fun DashboardPreview() {
	DashboardScreen(
		uiState = MainWordUIState(),
		nextWordAction = {},
		navigateToDetailsAction = {}
	)
}