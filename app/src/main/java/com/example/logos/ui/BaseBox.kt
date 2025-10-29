package com.example.logos.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun BaseScreenBox(
	modifier: Modifier = Modifier,
	background: Color? = null,
	useSystemBarsPadding: Boolean = true,
	contentAlignment: Alignment = Alignment.Center,
	content: @Composable BoxScope.() -> Unit
) {
	val bgColor = background ?: MaterialTheme.colorScheme.background
	val baseModifier = if (useSystemBarsPadding) modifier.systemBarsPadding() else modifier
	Box(
		modifier = baseModifier
			.background(bgColor)
			.padding(bottom = 24.dp),
		contentAlignment = contentAlignment
	) {
		content()
	}
}