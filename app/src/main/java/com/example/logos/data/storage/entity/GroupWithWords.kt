package com.example.logos.data.storage.entity

import androidx.room.Embedded
import androidx.room.Relation

data class GroupWithWords(
    @Embedded
    val group: GroupEntity,
    @Relation(
        parentColumn = "id",
        entityColumn = "groupId"
    )
    val words: List<WordEntity>
)