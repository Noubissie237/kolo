package com.propentatech.kolo.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.propentatech.kolo.data.local.entities.SavingEntryEntity
import kotlinx.coroutines.flow.Flow

/**
 * Data Access Object for [SavingEntryEntity].
 */
@Dao
interface SavingEntryDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertEntry(entry: SavingEntryEntity): Long

    @Delete
    suspend fun deleteEntry(entry: SavingEntryEntity)

    @Query("SELECT * FROM saving_entries WHERE projectId = :projectId ORDER BY createdAt DESC")
    fun getEntriesByProjectId(projectId: Long): Flow<List<SavingEntryEntity>>

    /**
     * Total amount saved for a project.
     */
    @Query("SELECT COALESCE(SUM(amount), 0) FROM saving_entries WHERE projectId = :projectId")
    fun getTotalSavedAmount(projectId: Long): Flow<Double>

    @Query("SELECT COUNT(*) FROM saving_entries WHERE projectId = :projectId")
    fun getEntryCount(projectId: Long): Flow<Int>

    @Query("DELETE FROM saving_entries WHERE projectId = :projectId")
    suspend fun deleteEntriesByProjectId(projectId: Long)

    @Query("DELETE FROM saving_entries")
    suspend fun deleteAllEntries()

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllEntries(entries: List<SavingEntryEntity>): List<Long>
}
