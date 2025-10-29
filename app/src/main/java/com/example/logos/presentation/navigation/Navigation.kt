package com.example.logos.presentation.navigation

import android.os.Bundle
import androidx.navigation.NavController
import androidx.navigation.NavOptionsBuilder
import androidx.navigation.navOptions

fun NavController.navigateToWordsList(bundle: Bundle) {
    navigateWithArguments(route = NavRoute.WORDS_LIST, args = bundle)
}

fun NavController.navigateToDetails(bundle: Bundle) {
    navigateWithArguments(route = NavRoute.WORD_DETAILS, args = bundle)
}

private fun NavController.navigateWithArguments(
    route: NavRoute,
    args: Bundle,
    builder: (NavOptionsBuilder.() -> Unit)? = null
) {
    val nodeId = graph.findNode(route = route.name)?.id
    if (nodeId != null) {
        if (builder != null) {
            navigate(nodeId, args, navOptions(builder))
        } else {
            navigate(nodeId, args)
        }
    }
}