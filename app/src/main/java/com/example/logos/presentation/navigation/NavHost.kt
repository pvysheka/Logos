package com.example.logos.presentation.navigation

import android.annotation.SuppressLint
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.material3.MaterialTheme
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.example.logos.presentation.word.list.wordsListScreen
import com.example.logos.presentation.word.details.wordDetailsScreen
import com.example.logos.presentation.group.list.groupsScreen
import com.example.logos.presentation.group.new_group.NewGroupScreen
import com.example.logos.presentation.word.new_word.NewWordScreen
import com.example.logos.ui.ExpandableFAB

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun LogosNavHost(navController: NavHostController = rememberNavController()) {
    val bottomSheetState = rememberModalBottomSheetState()
    var showBottomSheet by remember { mutableStateOf(false) }
    var sheetType by remember { mutableStateOf<SheetType?>(null) }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        floatingActionButton = {
            ExpandableFAB(
                onSmallFab1Click = {
                    sheetType = SheetType.GROUP
                    showBottomSheet = true
                },
                onSmallFab2Click = {
                    sheetType = SheetType.WORD
                    showBottomSheet = true
                }
            )
        }
    ) {
        NavHost(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background),
            navController = navController,
            startDestination = NavRoute.GROUPS.name,
            enterTransition = { EnterTransition.None },
            exitTransition = { ExitTransition.None }
        ) {
            groupsScreen(navController)
            wordsListScreen(navController)
            wordDetailsScreen(navController)
        }
    }

    if (showBottomSheet) {
        ModalBottomSheet(
            onDismissRequest = { showBottomSheet = false },
            sheetState = bottomSheetState,
        ) {
            when (sheetType) {
                SheetType.GROUP -> NewGroupScreen(clickAction = { showBottomSheet = false })
                SheetType.WORD -> NewWordScreen(clickAction = { showBottomSheet = false })
                null -> Unit
            }
        }
    }
}

private enum class SheetType {
    GROUP, WORD
}
