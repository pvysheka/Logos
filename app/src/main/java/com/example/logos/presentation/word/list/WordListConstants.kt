package com.example.logos.presentation.word.list

const val NO_GROUP_ID: Long = -1L

fun Long?.isValidGroupId(): Boolean = this != null && this != NO_GROUP_ID

