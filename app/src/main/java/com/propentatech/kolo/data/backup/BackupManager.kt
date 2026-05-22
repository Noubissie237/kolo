package com.propentatech.kolo.data.backup

import android.content.Context
import android.net.Uri
import com.propentatech.kolo.data.repository.KoloRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.withContext
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import java.io.IOException

/**
 * Manages backup and restore operations for Kolo data.
 * 
 * Exports all data to JSON and imports from JSON files.
 */
class BackupManager(
    private val context: Context,
    private val repository: KoloRepository
) {

    private val json = Json {
        prettyPrint = true
        ignoreUnknownKeys = true
    }

    /**
     * Export all data to a JSON string.
     */
    suspend fun exportToJson(): String = withContext(Dispatchers.IO) {
        val projects = repository.allProjects.first()
        val items = mutableListOf<com.propentatech.kolo.data.local.entities.ProjectItemEntity>()
        val entries = mutableListOf<com.propentatech.kolo.data.local.entities.SavingEntryEntity>()

        // Collect all items and entries for all projects
        projects.forEach { project ->
            items.addAll(repository.getItemsByProjectId(project.id).first())
            entries.addAll(repository.getEntriesByProjectId(project.id).first())
        }

        val backupData = BackupData(
            projects = projects,
            items = items,
            entries = entries
        )

        json.encodeToString(backupData)
    }

    /**
     * Write backup JSON to a URI (file).
     */
    suspend fun writeBackupToUri(uri: Uri, jsonContent: String) = withContext(Dispatchers.IO) {
        try {
            context.contentResolver.openOutputStream(uri)?.use { outputStream ->
                outputStream.write(jsonContent.toByteArray())
            } ?: throw IOException("Could not open output stream")
        } catch (e: Exception) {
            throw IOException("Failed to write backup: ${e.message}", e)
        }
    }

    /**
     * Read backup JSON from a URI (file).
     */
    suspend fun readBackupFromUri(uri: Uri): String = withContext(Dispatchers.IO) {
        try {
            context.contentResolver.openInputStream(uri)?.use { inputStream ->
                inputStream.bufferedReader().readText()
            } ?: throw IOException("Could not open input stream")
        } catch (e: Exception) {
            throw IOException("Failed to read backup: ${e.message}", e)
        }
    }

    /**
     * Import data from JSON string.
     * This will DELETE all existing data before importing.
     */
    suspend fun importFromJson(jsonContent: String) = withContext(Dispatchers.IO) {
        try {
            val backupData = json.decodeFromString<BackupData>(jsonContent)
            
            // Delete all existing data
            repository.deleteAllData()
            
            // Restore from backup
            repository.restoreData(
                projects = backupData.projects,
                items = backupData.items,
                entries = backupData.entries
            )
        } catch (e: Exception) {
            throw IOException("Failed to import backup: ${e.message}", e)
        }
    }
}
