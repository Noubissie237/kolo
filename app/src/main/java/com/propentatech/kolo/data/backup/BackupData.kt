package com.propentatech.kolo.data.backup

import com.propentatech.kolo.data.local.entities.ProjectEntity
import com.propentatech.kolo.data.local.entities.ProjectItemEntity
import com.propentatech.kolo.data.local.entities.SavingEntryEntity
import kotlinx.serialization.Serializable

/**
 * Data class for backup/restore operations.
 * 
 * This structure is serialized to JSON for export
 * and deserialized from JSON for import.
 */
@Serializable
data class BackupData(
    val version: Int = 1,
    val exportDate: Long = System.currentTimeMillis(),
    val projects: List<ProjectEntity>,
    val items: List<ProjectItemEntity>,
    val entries: List<SavingEntryEntity>
)
