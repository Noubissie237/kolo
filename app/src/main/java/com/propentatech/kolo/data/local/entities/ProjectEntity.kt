package com.propentatech.kolo.data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.serialization.Serializable

/**
 * Represents a user's savings project.
 *
 * Examples: "Vacation trip", "Buy a computer", "Wedding", etc.
 * The targetDate represents when the user aims to have the full amount saved.
 */
@Serializable
@Entity(tableName = "projects")
data class ProjectEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val title: String,
    val description: String? = null,
    val targetDate: Long, // Stored as epoch millis
    val createdAt: Long = System.currentTimeMillis(),
    val updatedAt: Long = System.currentTimeMillis()
)
