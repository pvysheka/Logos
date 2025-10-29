package com.example.logos.presentation.navigation

enum class NavRoute {
	GROUPS,
	GROUP_DETAILS,
	WORDS_LIST,
	WORD_DETAILS
}

fun String?.toNavRoute() = this?.let { NavRoute.valueOf(it) }