package com.example.logos.ui

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.shape.RoundedCornerShape

@Composable
fun ExpandableFAB(
    mainIcon: @Composable () -> Unit = { Icon(Icons.Filled.Add, "Add") },
    smallFab1Icon: @Composable () -> Unit = { Icon(Icons.Filled.Person, "New group") },
    smallFab2Icon: @Composable () -> Unit = { Icon(Icons.Filled.Edit, "New word") },
    smallFabSize: Dp = 48.dp,
    spacing: Dp = 12.dp,
    showLabels: Boolean = true,
    labelShape: Shape = RoundedCornerShape(12.dp),
    onSmallFab1Click: () -> Unit = {},
    onSmallFab2Click: () -> Unit = {},
) {
    var expanded by remember { mutableStateOf(false) }

    // animate rotation angle for main icon
    val rotationAngle by animateFloatAsState(
        targetValue = if (expanded) 45f else 0f,
        animationSpec = tween(200)
    )

    Column(horizontalAlignment = Alignment.End, verticalArrangement = Arrangement.Bottom) {
        // Top small FAB (New Word)
        AnimatedVisibility(
            visible = expanded,
            enter = slideInVertically(
                initialOffsetY = { it },
                animationSpec = tween(180)
            ) + fadeIn(),
            exit = slideOutVertically(
                targetOffsetY = { it },
                animationSpec = tween(120)
            ) + fadeOut()
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.End
            ) {
                if (showLabels) {
                    Surface(
                        shape = labelShape,
                        color = MaterialTheme.colorScheme.surfaceVariant,
                        tonalElevation = 2.dp
                    ) {
                        Text(
                            text = "New word",
                            modifier = Modifier.padding(horizontal = 12.dp, vertical = 8.dp),
                            color = MaterialTheme.colorScheme.onSurface,
                            fontWeight = FontWeight.Medium
                        )
                    }
                    Spacer(modifier = Modifier.padding(8.dp))
                }

                FloatingActionButton(
                    onClick = {
                        expanded = false
                        onSmallFab2Click()
                    },
                    modifier = Modifier.size(smallFabSize)
                ) {
                    smallFab2Icon()
                }
            }
        }

        Spacer(modifier = Modifier.padding(spacing))

        // Lower small FAB (New Group)
        AnimatedVisibility(
            visible = expanded,
            enter = slideInVertically(
                initialOffsetY = { it },
                animationSpec = tween(180)
            ) + fadeIn(),
            exit = slideOutVertically(
                targetOffsetY = { it },
                animationSpec = tween(120)
            ) + fadeOut()
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.End
            ) {
                if (showLabels) {
                    Surface(
                        shape = labelShape,
                        color = MaterialTheme.colorScheme.surfaceVariant,
                        tonalElevation = 2.dp
                    ) {
                        Text(
                            text = "New group",
                            modifier = Modifier.padding(horizontal = 12.dp, vertical = 8.dp),
                            color = MaterialTheme.colorScheme.onSurface,
                            fontWeight = FontWeight.Medium
                        )
                    }
                    Spacer(modifier = Modifier.padding(8.dp))
                }

                FloatingActionButton(
                    onClick = {
                        expanded = false
                        onSmallFab1Click()
                    },
                    modifier = Modifier.size(smallFabSize)
                ) {
                    smallFab1Icon()
                }
            }
        }

        Spacer(modifier = Modifier.padding(spacing))

        // Main FAB
        FloatingActionButton(
            onClick = { expanded = !expanded }
        ) {
            // rotate main icon
            androidx.compose.foundation.layout.Box(modifier = Modifier.rotate(rotationAngle)) {
                mainIcon()
            }
        }
    }
}
