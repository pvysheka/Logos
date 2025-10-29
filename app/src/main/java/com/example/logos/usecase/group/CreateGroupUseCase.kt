package com.example.logos.usecase.group

import com.example.logos.data.repositoty.DatabaseRepository
import com.example.logos.data.storage.entity.GroupEntity
import com.example.logos.data.storage.entity.WordEntity
import javax.inject.Inject

class CreateGroupUseCase @Inject constructor(
    private val databaseRepository: DatabaseRepository
) {

    suspend operator fun invoke(title: String) =
        databaseRepository.saveGroup(
            group = GroupEntity(title = title)
        )
}