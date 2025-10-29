package com.example.logos.presentation.group.new_group

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.logos.ui.InputField

@Composable
fun NewGroupScreen(
	clickAction: () -> Unit = {}
) {
	val viewModel: NewGroupViewModel = hiltViewModel()

	val titleFieldState = remember { mutableStateOf("") }

	Column(
		modifier = Modifier.fillMaxSize(),
		horizontalAlignment = Alignment.CenterHorizontally
	) {
		InputField(
			state = titleFieldState,
			hint = "Group title"
		)

		ConfirmButton(
			clickAction = {
				viewModel.saveGroup(titleFieldState.value)
				clickAction.invoke()
			}
		)
	}
}

@Composable
fun ConfirmButton(
	modifier: Modifier = Modifier,
	enabled: Boolean = true,
	clickAction: () -> Unit
) {
	Button(
		modifier = modifier
			.fillMaxWidth()
			.padding(24.dp),
		enabled = enabled,
		onClick = clickAction
	) {
		Text(
			text = "Add",
		)
	}
}