package com.example.logos.ui.card


import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.logos.data.storage.entity.WordEntity
import com.example.logos.ui.ProgressIndicator

@Composable
fun SimpleWordCard(
	word: WordEntity? = null,
	modifier: Modifier = Modifier,
) {
	Card(
		modifier = modifier
			.padding(8.dp),
		shape = RoundedCornerShape(12.dp),
		elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
	) {
		BaseWordCardContent(
			modifier = modifier
		) {
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
}