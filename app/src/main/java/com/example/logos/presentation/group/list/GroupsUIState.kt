package com.example.logos.presentation.group.list

import com.example.logos.data.storage.entity.GroupEntity

data class GroupsUIState(
    val groups: List<GroupEntity> = emptyList(),
)