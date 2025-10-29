package com.example.logos.presentation.group.list

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.os.bundleOf
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.example.logos.presentation.group.details.ARGUMENT_GROUP_ID
import com.example.logos.presentation.navigation.NavRoute
import com.example.logos.presentation.navigation.navigateToWordsList
import com.example.logos.ui.BaseScreenBox
import com.example.logos.ui.group.GroupCard
import com.example.logos.ui.theme.LogosTheme

fun NavGraphBuilder.groupsScreen(
    navController: NavController
) {
    composable(route = NavRoute.GROUPS.name) {
        val viewModel: GroupsViewModel = hiltViewModel()
        val uiState by viewModel.uiState.collectAsState()

        GroupsScreen(
            uiState = uiState,
            navigateToGroupDetailsAction = { groupId ->
                navController.navigateToWordsList(bundleOf(ARGUMENT_GROUP_ID to groupId))
            }
        )
    }
}

@Composable
fun GroupsScreen(
    uiState: GroupsUIState,
    navigateToGroupDetailsAction: (groupId: Long) -> Unit,
) {

    BaseScreenBox(modifier = Modifier.fillMaxSize()) {
        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(16.dp)
        ) {
            items(uiState.groups) { group ->
                GroupCard(
                    title = group.title,
                    onClick = { navigateToGroupDetailsAction(group.id) }
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GroupsScreenPreview() {
    LogosTheme {
        BaseScreenBox(modifier = Modifier.fillMaxSize()) {
            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(16.dp)
            ) {
                items((1..6).toList()) { index ->
                    GroupCard(
                        title = "Sample Group $index",
                        onClick = {}
                    )
                }
            }
        }
    }
}