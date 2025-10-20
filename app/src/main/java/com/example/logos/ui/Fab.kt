package com.example.logos.ui

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable

@Composable
fun FAB(
	clickAction: () -> Unit = {},
) {
	FloatingActionButton(
		onClick = clickAction,
	) {
		Icon(Icons.Filled.Add, "Floating action button.")
	}
}