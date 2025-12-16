package com.example.logos.presentation.word.new_word

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.logos.data.storage.entity.GroupEntity
import com.example.logos.ui.InputField

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NewWordScreen(
    clickAction: () -> Unit = {},
    preselectedGroupId: Long? = null,
) {
    val viewModel: NewWordViewModel = hiltViewModel()
    val uiState by viewModel.uiState.collectAsState()

    LaunchedEffect(preselectedGroupId) {
        viewModel.setPreselectedGroup(preselectedGroupId)
    }

    NewWordContent(
        uiState = uiState,
        onSelectGroup = { viewModel.selectGroup(it.id) },
        onSaveWord = { word, meaning ->
            viewModel.saveWord(word, meaning)
            clickAction.invoke()
        },
        showGroupPicker = preselectedGroupId == null
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun NewWordContent(
    uiState: NewWordUiState,
    onSelectGroup: (GroupEntity) -> Unit,
    onSaveWord: (String, String) -> Unit,
    showGroupPicker: Boolean,
) {
    val wordFieldStat = remember { mutableStateOf("") }
    val meaningFieldStat = remember { mutableStateOf("") }
    val expanded = remember { mutableStateOf(false) }

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        InputField(state = wordFieldStat, hint = "Word")
        Spacer(Modifier.height(20.dp))
        InputField(state = meaningFieldStat, hint = "Meaning")
        Spacer(Modifier.height(20.dp))

        if (showGroupPicker) {
            GroupDropdown(
                groups = uiState.groups,
                selectedGroup = uiState.selectedGroup,
                expanded = expanded.value,
                onExpandedChange = { expanded.value = it },
                onSelectGroup = {
                    onSelectGroup(it)
                    expanded.value = false
                }
            )
        } else {
            Spacer(Modifier.height(20.dp))
        }

        ConfirmButton(
            clickAction = {
                onSaveWord(wordFieldStat.value, meaningFieldStat.value)
            }
        )
    }
}


@Composable
@OptIn(ExperimentalMaterial3Api::class)
private fun GroupDropdown(
    groups: List<GroupEntity>,
    selectedGroup: GroupEntity?,
    expanded: Boolean,
    onExpandedChange: (Boolean) -> Unit,
    onSelectGroup: (GroupEntity) -> Unit
) {
    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = onExpandedChange,
    ) {
        TextField(
            modifier = Modifier
                .menuAnchor()
                .fillMaxWidth()
                .padding(horizontal = 24.dp),
            readOnly = true,
            value = selectedGroup?.title ?: "Select group",
            onValueChange = {},
            label = { Text("Group") },
            trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
            colors = ExposedDropdownMenuDefaults.textFieldColors()
        )
        ExposedDropdownMenu(
            expanded = expanded,
            onDismissRequest = { onExpandedChange(false) }
        ) {
            groups.forEach { group ->
                DropdownMenuItem(
                    text = { Text(group.title) },
                    onClick = { onSelectGroup(group) },
                    contentPadding = ExposedDropdownMenuDefaults.ItemContentPadding
                )
            }
        }
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