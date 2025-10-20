package com.example.logos.ui

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.logos.CORRECT_WORD_ANSWERS_COUNT

@Composable
fun ProgressIndicator(
	modifier: Modifier = Modifier,
	totalDots: Int = CORRECT_WORD_ANSWERS_COUNT,
	filledDots: Int,
	dotColorFilled: Color = MaterialTheme.colorScheme.primary,
	dotColorEmpty: Color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.3f),
	dotSize: Dp = 8.dp,
	spacing: Dp = 4.dp,
	animationDurationMillis: Int = 300 // Duration for color change animation
) {
	// Ensure filledDots doesn't exceed totalDots and is not negative
	val actualFilledDots = filledDots.coerceIn(0, totalDots)

	Row(
		modifier = modifier,
		verticalAlignment = Alignment.CenterVertically,
		horizontalArrangement = Arrangement.Center
	) {
		repeat(totalDots) { index ->
			val isFilled = index < actualFilledDots

			// Animate the color change for a smoother transition
			val currentColor by animateColorAsState(
				targetValue = if (isFilled) getProgressColor(filledDots.toDouble() / totalDots) else dotColorEmpty,
				animationSpec = tween(durationMillis = animationDurationMillis),
				label = "dotColorAnimation"
			)

			Box(
				modifier = Modifier
					.size(dotSize)
					.background(color = currentColor, shape = CircleShape)
			)

			if (index < totalDots - 1) {
				Spacer(Modifier.width(spacing))
			}
		}
	}
}

private fun getProgressColor(
	percent: Double
) = when {
	percent < 0.34 -> Color.Blue
	percent < 0.67 -> Color.Yellow
	else -> Color.Green
}

// --- Example Usage ---
@Preview(showBackground = true)
@Composable
fun PreviewSegmentedDotProgressIndicator() {
	MaterialTheme {
		Column(
			modifier = Modifier
				.fillMaxSize()
				.padding(20.dp),
			horizontalAlignment = Alignment.CenterHorizontally,
			verticalArrangement = Arrangement.Center
		) {
			Text("Progress: 0 of 5", style = MaterialTheme.typography.bodyLarge)
			Spacer(Modifier.height(10.dp))
			ProgressIndicator(totalDots = 5, filledDots = 0)
			Spacer(Modifier.height(20.dp))

			Text("Progress: 1 of 5", style = MaterialTheme.typography.bodyLarge)
			Spacer(Modifier.height(10.dp))
			ProgressIndicator(totalDots = 5, filledDots = 1)
			Spacer(Modifier.height(20.dp))

			Text("Progress: 3 of 5", style = MaterialTheme.typography.bodyLarge)
			Spacer(Modifier.height(10.dp))
			ProgressIndicator(totalDots = 5, filledDots = 3)
			Spacer(Modifier.height(20.dp))

			Text("Progress: 5 of 5", style = MaterialTheme.typography.bodyLarge)
			Spacer(Modifier.height(10.dp))
			ProgressIndicator(totalDots = 5, filledDots = 5)
			Spacer(Modifier.height(20.dp))

			Text("Custom Colors & Size", style = MaterialTheme.typography.bodyLarge)
			Spacer(Modifier.height(10.dp))
			ProgressIndicator(
				totalDots = 4,
				filledDots = 2,
				dotColorFilled = Color.Green,
				dotColorEmpty = Color.LightGray,
				dotSize = 12.dp,
				spacing = 6.dp
			)
		}
	}
}