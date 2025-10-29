package com.example.logos.data.storage.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import com.example.logos.data.storage.entity.GroupEntity
import com.example.logos.data.storage.entity.GroupWithWords
import kotlinx.coroutines.flow.Flow

@Dao
interface GroupDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertGroup(group: GroupEntity): Long

    @Query("SELECT * FROM GroupEntity")
    fun getAllGroups(): Flow<List<GroupEntity>>

    @Update
    suspend fun updateGroup(group: GroupEntity)

    @Transaction
    @Query("SELECT * FROM GroupEntity")
    fun getGroupsWithWords(): Flow<List<GroupWithWords>>

    @Transaction
    @Query("SELECT * FROM GroupEntity WHERE id = :groupId LIMIT 1")
    suspend fun getGroupWithWordsById(groupId: Long): GroupWithWords

    @Delete
    suspend fun deleteGroup(group: GroupEntity)
}
