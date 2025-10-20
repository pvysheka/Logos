package com.example.logos.ui.card

import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.logos.R
import com.example.logos.data.storage.entity.WordEntity
import com.example.logos.ui.ProgressIndicator

@Composable
fun WordCardFrontContent(
	word: WordEntity? = null,
	modifier: Modifier = Modifier,
	detailsClickAction: () -> Unit = {},
) {
	BaseWordCardContent(
		modifier = modifier
	) {
		EditButton(
			clickAction = detailsClickAction
		)

		Text(
			text = word?.word ?: "No Data",
			textAlign = TextAlign.Center,
			modifier = Modifier
				.padding(8.dp)
		)

		ProgressIndicator(
			filledDots = word?.correctCount ?: 0
		)
	}
}

@Composable
fun EditButton(
	modifier: Modifier = Modifier,
	contentDescription: String = "",
	clickAction: () -> Unit
) {
	val interactionSource = remember { MutableInteractionSource() }
	val isPressed by interactionSource.collectIsPressedAsState()

	IconButton(
		modifier = modifier,
		onClick = clickAction,
		interactionSource = interactionSource
	) {
		Icon(
			painter = painterResource(id = R.drawable.ic_edit),
			contentDescription = contentDescription
		)
	}
}