package com.propentatech.kolo.data.local.entities

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import kotlinx.serialization.Serializable

/**
 * Represents an item within a project — something the user needs to plan for.
 *
 * Examples:
 *   - "Plane ticket" → 500000 FCFA (requiresSaving = true)
 *   - "Sunglasses" → 0 FCFA (requiresSaving = false, just a reminder)
 *
 * Only items with requiresSaving = true count toward the project's financial goal.
 */
@Serializable
@Entity(
    tableName = "project_items",
    foreignKeys = [
        ForeignKey(
            entity = ProjectEntity::class,
            parentColumns = ["id"],
            childColumns = ["projectId"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [Index("projectId")]
)
data class ProjectItemEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val projectId: Long,
    val title: String,
    val amount: Double = 0.0,
    val requiresSaving: Boolean = true
)
