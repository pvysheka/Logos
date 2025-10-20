package com.example.logos.ui.card

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.logos.R
import com.example.logos.data.storage.entity.WordEntity
import com.example.logos.ui.ProgressIndicator

@Composable
fun WordCardBackContent(
	word: WordEntity? = null,
	modifier: Modifier = Modifier,
	correctClickAction: () -> Unit = {},
	wrongClickAction: () -> Unit = {},
) {
	BaseWordCardContent(
		modifier = modifier
	) {
		IconButton(
			modifier = modifier,
			onClick = wrongClickAction
		) {
			Icon(
				painter = painterResource(id = R.drawable.ic_wrong),
				contentDescription = null
			)
		}

		Column(
			horizontalAlignment = Alignment.CenterHorizontally,
			verticalArrangement = Arrangement.SpaceAround
		) {
			Text(
				text = "${word?.translations}",
				textAlign = TextAlign.Center,
				modifier = Modifier
					.padding(8.dp)
			)

			ProgressIndicator(
				filledDots = word?.correctCount ?: 0
			)
		}

		IconButton(
			modifier = modifier,
			onClick = correctClickAction
		) {
			Icon(
				painter = painterResource(id = R.drawable.ic_correct),
				contentDescription = null
			)
		}
	}
}