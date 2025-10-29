package com.example.logos.presentation.word.new_word

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
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
fun NewWordScreen(
    clickAction: () -> Unit = {}
) {
    val viewModel: NewWordViewModel = hiltViewModel()

    val wordFieldStat = remember { mutableStateOf("") }
    val meaningFieldStat = remember { mutableStateOf("") }

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        InputField(state = wordFieldStat, hint = "Word")
        Spacer(Modifier.height(20.dp))
        InputField(state = meaningFieldStat, hint = "Meaning")

        ConfirmButton(
            clickAction = {
                viewModel.saveWord(wordFieldStat.value, meaningFieldStat.value)
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