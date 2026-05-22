package com.propentatech.kolo.data.local.entities

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import kotlinx.serialization.Serializable

/**
 * Represents a savings deposit made by the user toward a project.
 *
 * Each entry records when the user saved money and optionally why.
 * Example: +50000 FCFA — "Salary bonus this month"
 */
@Serializable
@Entity(
    tableName = "saving_entries",
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
data class SavingEntryEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val projectId: Long,
    val amount: Double,
    val description: String? = null,
    val createdAt: Long = System.currentTimeMillis()
)
