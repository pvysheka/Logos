package com.example.logos.ui

import android.annotation.SuppressLint
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.unit.dp
import com.example.logos.data.storage.entity.WordEntity
import com.example.logos.ui.card.SimpleWordCard
import com.example.logos.ui.card.WordCard
import kotlinx.coroutines.launch


@Composable
fun CardList(
	words: List<WordEntity>,
	nextWordAction: () -> Unit = {},
	navigateToDetailsAction: () -> Unit = {},
	correctClickAction: () -> Unit = {},
	wrongClickAction: () -> Unit = {},
) {
	val topCard = words.firstOrNull()
	val nextCard = words.getOrNull(1)
	val remainingCards = words.drop(2)

	var lastDismissedId by remember { mutableLongStateOf(-1) }

	var swipedCardId by remember { mutableLongStateOf(-1) }

	/*	Column (
			Modifier
				.fillMaxSize()
				.padding(16.dp)
		) {
			// 1. Ведущая карточка
			if (topCard != null && topCard.id != swipedCardId) {
				WordCard(
					topCard,
					modifier = Modifier
						.padding(8.dp)
						.height(120.dp),
					detailsClickAction = navigateToDetailsAction,
					correctClickAction = {
						swipedCardId = topCard.id
						correctClickAction()
					},
					wrongClickAction = {
						swipedCardId = topCard.id
						wrongClickAction()
					},
				)
			}

			// 2. Анимируем следующую
			if (nextCard != null && swipedCardId != -1L) {
				AnimatedCardTransitioningToTop(
					word = nextCard,
					correctClickAction = correctClickAction,
					wrongClickAction = wrongClickAction,
				)
			}

			// 3. Маленькие карточки
			LazyColumn {
				items(remainingCards, key = { it.id }) { word ->
					SimpleWordCard(word = word)
				}
			}
		}*/

	Column(
		modifier = Modifier
			.fillMaxSize()
			.padding(16.dp)
	) {
		/*		AnimatedContent1(
			targetState = words.firstOrNull(),
			transitionSpec = {
				fadeIn(tween(300)) togetherWith fadeOut(tween(300))
			},
			label = "Top Card Animation"
		) { topCard ->
			topCard?.let {
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
			}
		}

		Spacer(modifier = Modifier.height(24.dp))*/

		LazyColumn {
			itemsIndexed(words, key = { _, word -> word.id }) { index, word ->
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

				/*items(words, key = { it.id }) { word ->
					if (word.id == topCard?.id && word.id != lastDismissedId) {
						AnimatedCardTransitioningToTop(word = word)
					} else {
						SimpleWordCard(word = word)
					}
				}*/
			}
		}
	}
}

@SuppressLint("RememberReturnType")
@Composable
fun AnimatedCardTransitioningToTop(
	word: WordEntity,
	correctClickAction: () -> Unit = {},
	wrongClickAction: () -> Unit = {},
) {
	val offsetY = remember { Animatable(200f) }
	val scale = remember { Animatable(0.8f) }
	val alpha = remember { Animatable(0.5f) }

	LaunchedEffect(Unit) {
		launch {
			offsetY.animateTo(
				targetValue = 0f,
				animationSpec = tween(400, easing = FastOutSlowInEasing)
			)
		}
		launch {
			scale.animateTo(
				targetValue = 1f,
				animationSpec = tween(400)
			)
		}
		launch {
			alpha.animateTo(
				targetValue = 1f,
				animationSpec = tween(400)
			)
		}
	}


	WordCard(
		word = word,
		modifier = Modifier
			.graphicsLayer {
				translationY = offsetY.value
				scaleX = scale.value
				scaleY = scale.value
				this.alpha = alpha.value
			}
			.padding(8.dp)
			.height(120.dp),
		wrongClickAction = wrongClickAction,
		correctClickAction = correctClickAction
	)

	/*	Box(
			modifier = Modifier
				.graphicsLayer {
					translationY = offsetY.value
					scaleX = scale.value
					scaleY = scale.value
					this.alpha = alpha.value
				}
				.fillMaxWidth()
				.height(200.dp)
				.background(Color.LightGray, RoundedCornerShape(16.dp)),
			contentAlignment = Alignment.Center
		) {
			Text(
				text = word.word,
				fontSize = 24.sp,
				fontWeight = FontWeight.Bold
			)
		}*/
}