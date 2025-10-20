package com.example.logos.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.unit.dp

@Composable
fun BaseScreenBox(
	modifier: Modifier = Modifier,
	background: Color = White,
	contentAlignment: Alignment = Alignment.Center,
	content: @Composable BoxScope.() -> Unit
) {
	Box(
		modifier = modifier
			.background(background)
			.padding(bottom = 24.dp),
		contentAlignment = contentAlignment
	) {
		content()
	}
}