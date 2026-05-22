package com.propentatech.kolo.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.propentatech.kolo.data.local.entities.ProjectItemEntity
import kotlinx.coroutines.flow.Flow

/**
 * Data Access Object for [ProjectItemEntity].
 */
@Dao
interface ProjectItemDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertItem(item: ProjectItemEntity): Long

    @Update
    suspend fun updateItem(item: ProjectItemEntity)

    @Delete
    suspend fun deleteItem(item: ProjectItemEntity)

    @Query("SELECT * FROM project_items WHERE projectId = :projectId")
    fun getItemsByProjectId(projectId: Long): Flow<List<ProjectItemEntity>>

    @Query("SELECT * FROM project_items WHERE id = :itemId")
    fun getItemById(itemId: Long): Flow<ProjectItemEntity?>

    /**
     * Sum of amounts for items that require saving.
     * This gives the total financial target for a project.
     */
    @Query("SELECT COALESCE(SUM(amount), 0) FROM project_items WHERE projectId = :projectId AND requiresSaving = 1")
    fun getTotalTargetAmount(projectId: Long): Flow<Double>

    @Query("DELETE FROM project_items WHERE projectId = :projectId")
    suspend fun deleteItemsByProjectId(projectId: Long)

    @Query("DELETE FROM project_items")
    suspend fun deleteAllItems()

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllItems(items: List<ProjectItemEntity>): List<Long>
}
