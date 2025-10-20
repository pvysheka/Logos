package com.example.logos.ui.card

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.logos.data.storage.entity.WordEntity

@Composable
fun WordCard(
	word: WordEntity?,
	modifier: Modifier = Modifier,
	clickAction: () -> Unit = {},
	detailsClickAction: () -> Unit = {},
	correctClickAction: () -> Unit = {},
	wrongClickAction: () -> Unit = {},
) {
	var rotateToFront by remember { mutableStateOf(false) }

	FlippableCard(
		modifier = modifier,
		clickAction = clickAction,
		onSwipeLeft = wrongClickAction,
		onSwipeRight = correctClickAction,
		toFront = rotateToFront,
		frontContent = {
			WordCardFrontContent(
				word = word,
				detailsClickAction = detailsClickAction
			)
		},
		backContent = {
			WordCardBackContent(
				word = word,
				correctClickAction = {
					rotateToFront = true
					correctClickAction()
					rotateToFront = false
				},
				wrongClickAction = {
					rotateToFront = true
					wrongClickAction()
					rotateToFront = false
				}
			)
		}
	)
}

@Preview
@Composable
fun WordCardPreview() {
	WordCard(
		word = WordEntity(
			word = "Test",
			translations = listOf("translation test"),
			correctCount = 2
		),
		clickAction = {},
		detailsClickAction = {}
	)
}