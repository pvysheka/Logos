package com.example.logos.ui.card

import android.util.Log
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectHorizontalDragGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.input.pointer.positionChange
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay
import kotlin.math.roundToInt

private const val ROTATION_ANGLE_FRONT = 0f
private const val ROTATION_ANGLE_BACK = 180f
private const val ROTATION_ANGLE_MIDDLE = 90f

private const val ROTATION_DURATION = 800

private const val CAMERA_DISTANCE_X = 12f
private const val SENSITIVITY_FACTOR = 3f

enum class CardFace(val angle: Float) {
	FRONT(ROTATION_ANGLE_FRONT),
	BACK(ROTATION_ANGLE_BACK)
}

@Composable
fun FlippableCard(
	modifier: Modifier = Modifier,
	initialFace: CardFace = CardFace.FRONT,
	cameraDistanceX: Float = CAMERA_DISTANCE_X, // Adjust for perspective effect
	frontContent: @Composable () -> Unit,
	backContent: @Composable () -> Unit,
	clickAction: () -> Unit = {}, // Optional additional click action for the card
	onSwipeLeft: () -> Unit = {},
	onSwipeRight: () -> Unit = {},
	swipeThreshold: Float = 400f,
	sensitivityFactor: Float = SENSITIVITY_FACTOR,
	toFront: Boolean = false,
	dismissRightManually: Boolean = false,
	dismissLeftManually: Boolean = false,
) {
	var cardFace by remember { mutableStateOf(initialFace) }
	var offset by remember { mutableFloatStateOf(0f) }
	var dismissRight by remember { mutableStateOf(false) }
	var dismissLeft by remember { mutableStateOf(false) }
	val density = LocalDensity.current.density
	val rotation by animateFloatAsState(
		targetValue = cardFace.angle,
		animationSpec = tween(
			durationMillis = ROTATION_DURATION,
			easing = FastOutSlowInEasing
		)
	)

	LaunchedEffect(dismissRight) {
		if (dismissRight) {
			delay(300)
			onSwipeRight()
			dismissRight = false
		}
	}

	LaunchedEffect(dismissLeft) {
		if (dismissLeft) {
			delay(300)
			onSwipeLeft.invoke()
			dismissLeft = false
		}
	}

	LaunchedEffect(toFront) {
		if (toFront) {
			cardFace = CardFace.FRONT
		}
	}

	Box(
		modifier = Modifier
			.offset { IntOffset(offset.roundToInt(), 0) }
			.pointerInput(Unit) {
				detectHorizontalDragGestures(
					onDragEnd = { offset = 0f }
				) { change, dragAmount ->
					offset += (dragAmount / density) * sensitivityFactor

					when {
						offset > swipeThreshold -> { dismissRight = true }
						offset < -swipeThreshold -> { dismissLeft = true }
					}

					if (change.positionChange() != Offset.Zero) change.consume()
				}
			}
			.graphicsLayer(
				alpha = 10f - animateFloatAsState(if (dismissRight) 1f else 0f).value,
				rotationZ = animateFloatAsState(offset / 50).value
			)) {

		Card(
			modifier = modifier
				.graphicsLayer {
					rotationY = rotation // Apply the Y-axis rotation
					cameraDistance = cameraDistanceX * density // Add perspective
				}
				.clickable {
					cardFace = if (cardFace == CardFace.BACK) CardFace.FRONT else CardFace.BACK
					clickAction.invoke() // Invoke optional additional click action
				},
			shape = RoundedCornerShape(12.dp),
			elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
		) {
			// Display content based on rotation
			if (rotation <= ROTATION_ANGLE_MIDDLE) {
				// Front side visible
				Box(
					modifier = Modifier
						.fillMaxSize()
						// Revert rotation for the content itself so it doesn't appear mirrored
						.graphicsLayer { rotationY = ROTATION_ANGLE_FRONT },
					contentAlignment = Alignment.Center
				) {
					frontContent()
				}
			} else {
				// Back side visible
				Box(
					modifier = Modifier
						.fillMaxSize()
						// Apply inverse rotation for the content so it faces the user
						.graphicsLayer { rotationY = ROTATION_ANGLE_BACK },
					contentAlignment = Alignment.Center
				) {
					backContent()
				}
			}
		}

	}
}

// --- Example Usage ---

@Preview(showBackground = true)
@Composable
fun PreviewFlippableCard() {
	MaterialTheme {
		Column(
			modifier = Modifier
				.fillMaxSize()
				.background(Color.LightGray)
				.padding(24.dp),
			horizontalAlignment = Alignment.CenterHorizontally,
			verticalArrangement = Arrangement.spacedBy(24.dp)
		) {
			// Card 1: Basic Flip
			FlippableCard(
				modifier = Modifier
					.fillMaxWidth()
					.height(200.dp),
				frontContent = {
					Box(
						modifier = Modifier
							.fillMaxSize()
							.background(Color.Blue.copy(alpha = 0.8f)),
						contentAlignment = Alignment.Center
					) {
						Text("Front Side", color = Color.White, style = MaterialTheme.typography.headlineSmall)
					}
				},
				backContent = {
					Box(
						modifier = Modifier
							.fillMaxSize()
							.background(Color.Red.copy(alpha = 0.8f)),
						contentAlignment = Alignment.Center
					) {
						Text("Back Side", color = Color.White, style = MaterialTheme.typography.headlineSmall)
					}
				}
			)

			// Card 2: With an additional click action on the card itself
			FlippableCard(
				modifier = Modifier
					.fillMaxWidth()
					.height(200.dp),
				clickAction = {
					// This action runs whenever the card is clicked (before or after flip)
					println("Card 2 was clicked!")
				},
				frontContent = {
					Box(
						modifier = Modifier
							.fillMaxSize()
							.background(Color.Green.copy(alpha = 0.8f)),
						contentAlignment = Alignment.Center
					) {
						Column(horizontalAlignment = Alignment.CenterHorizontally) {
							Text("Click for Info", color = Color.White, style = MaterialTheme.typography.headlineSmall)
							Text("Full card click", color = Color.White, style = MaterialTheme.typography.bodySmall)
						}
					}
				},
				backContent = {
					Box(
						modifier = Modifier
							.fillMaxSize()
							.background(Color.Magenta.copy(alpha = 0.8f)),
						contentAlignment = Alignment.Center
					) {
						Text("More Details Here!", color = Color.White, style = MaterialTheme.typography.headlineSmall)
					}
				}
			)

			// Card 3: With an interactive element (Button) inside the content
			FlippableCard(
				modifier = Modifier
					.fillMaxWidth()
					.height(200.dp),
				frontContent = {
					Box(
						modifier = Modifier
							.fillMaxSize()
							.background(Color.DarkGray.copy(alpha = 0.8f)),
						contentAlignment = Alignment.Center
					) {
						Column(horizontalAlignment = Alignment.CenterHorizontally) {
							Text("Press button:", color = Color.White)
							Spacer(Modifier.height(8.dp))
							Button(onClick = { println("Button clicked on front!") }) {
								Text("Click Me!")
							}
						}
					}
				},
				backContent = {
					Box(
						modifier = Modifier
							.fillMaxSize()
							.background(Color.LightGray.copy(alpha = 0.8f)),
						contentAlignment = Alignment.Center
					) {
						Text("This is the back side with no button.", color = Color.Black)
					}
				}
			)
		}
	}
}