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
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.example.logos.presentation.dashboard.dashboardScreen
import com.example.logos.presentation.details.wordDetailsScreen
import com.example.logos.presentation.newWord.NewWordScreen
import com.example.logos.ui.FAB

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun LogosNavHost(navController: NavHostController = rememberNavController()) {
	val bottomSheetState = rememberModalBottomSheetState()
	var showBottomSheet by remember { mutableStateOf(false) }

	Scaffold(
		modifier = Modifier.fillMaxSize(),
		floatingActionButton = {
			FAB(
				clickAction = { showBottomSheet = true }
			)
		}
	) {
		NavHost(
			modifier = Modifier
				.fillMaxSize()
				.background(White),
			navController = navController,
			startDestination = NavRoute.DASHBOARD.name,
			enterTransition = { EnterTransition.None },
			exitTransition = { ExitTransition.None }
		) {
			dashboardScreen(navController)
			wordDetailsScreen(navController)
		}
	}

	if (showBottomSheet) {
		ModalBottomSheet(
			onDismissRequest = { showBottomSheet = false },
			sheetState = bottomSheetState,
		) {
			NewWordScreen(
				clickAction = { showBottomSheet = false }
			)
		}
	}
}