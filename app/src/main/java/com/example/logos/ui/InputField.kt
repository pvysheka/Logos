package com.example.logos.ui

import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember

@Composable
fun InputField(
	state: MutableState<String> = remember { mutableStateOf("") }
) {
	TextField(
		value = state.value,
		placeholder = { Text("Enter word") },
		onValueChange = { state.value = it }
	)
}