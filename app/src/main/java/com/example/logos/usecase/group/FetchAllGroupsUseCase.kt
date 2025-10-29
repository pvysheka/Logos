package com.example.logos.usecase.group

import com.example.logos.data.repositoty.DatabaseRepository
import javax.inject.Inject

class FetchAllGroupsUseCase@Inject constructor(
	private val databaseRepository: DatabaseRepository
) {

	operator fun invoke() = databaseRepository.getAllGroups()
}