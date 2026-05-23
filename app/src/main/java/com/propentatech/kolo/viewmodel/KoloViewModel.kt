package com.propentatech.kolo.viewmodel

import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.propentatech.kolo.data.backup.BackupManager
import com.propentatech.kolo.data.local.entities.ProjectEntity
import com.propentatech.kolo.data.local.entities.ProjectItemEntity
import com.propentatech.kolo.data.local.entities.SavingEntryEntity
import com.propentatech.kolo.data.preferences.KoloPreferences
import com.propentatech.kolo.data.repository.KoloRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

/**
 * Main ViewModel for the Kolo application.
 *
 * Handles all business logic and provides reactive state to the UI.
 * Uses combine to mix multiple data sources (target + saved) into single UI states.
 */
class KoloViewModel(
    private val repository: KoloRepository,
    private val preferences: KoloPreferences,
    private val backupManager: BackupManager
) : ViewModel() {

    // ========================================================
    // Preferences State
    // ========================================================

    val language: StateFlow<String> = preferences.language.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = KoloPreferences.DEFAULT_LANGUAGE
    )

    val isOnboardingCompleted: StateFlow<Boolean> = preferences.isOnboardingCompleted.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = false
    )

    fun setLanguage(language: String) {
        viewModelScope.launch {
            preferences.setLanguage(language)
        }
    }

    fun completeOnboarding() {
        viewModelScope.launch {
            preferences.setOnboardingCompleted(true)
        }
    }

    // ========================================================
    // Projects State
    // ========================================================

    val allProjects: StateFlow<List<ProjectEntity>> = repository.allProjects.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = emptyList()
    )

    fun getProjectById(projectId: Long): Flow<ProjectEntity?> = repository.getProjectById(projectId)

    fun createProject(title: String, description: String?, targetDate: Long) {
        viewModelScope.launch {
            repository.insertProject(
                ProjectEntity(
                    title = title,
                    description = description,
                    targetDate = targetDate
                )
            )
        }
    }

    fun updateProject(project: ProjectEntity) {
        viewModelScope.launch {
            repository.updateProject(project.copy(updatedAt = System.currentTimeMillis()))
        }
    }

    fun deleteProjectById(projectId: Long) {
        viewModelScope.launch {
            repository.deleteProjectById(projectId)
        }
    }

    // ========================================================
    // Project Details State (Combined)
    // ========================================================

    fun getProjectItems(projectId: Long): Flow<List<ProjectItemEntity>> =
        repository.getItemsByProjectId(projectId)

    fun getProjectSavings(projectId: Long): Flow<List<SavingEntryEntity>> =
        repository.getEntriesByProjectId(projectId)

    fun getProjectTargetAmount(projectId: Long): Flow<Double> =
        repository.getTotalTargetAmount(projectId)

    fun getProjectSavedAmount(projectId: Long): Flow<Double> =
        repository.getTotalSavedAmount(projectId)

    // ========================================================
    // Project Items Operations
    // ========================================================

    fun addProjectItem(projectId: Long, title: String, amount: Double, requiresSaving: Boolean) {
        viewModelScope.launch {
            repository.insertItem(
                ProjectItemEntity(
                    projectId = projectId,
                    title = title,
                    amount = amount,
                    requiresSaving = requiresSaving
                )
            )
        }
    }

    fun updateProjectItem(item: ProjectItemEntity) {
        viewModelScope.launch {
            repository.updateItem(item)
        }
    }

    fun deleteProjectItem(item: ProjectItemEntity) {
        viewModelScope.launch {
            repository.deleteItem(item)
        }
    }

    // ========================================================
    // Saving Entries Operations
    // ========================================================

    fun addSavingEntry(projectId: Long, amount: Double, description: String?) {
        viewModelScope.launch {
            repository.insertEntry(
                SavingEntryEntity(
                    projectId = projectId,
                    amount = amount,
                    description = description
                )
            )
        }
    }

    fun deleteSavingEntry(entry: SavingEntryEntity) {
        viewModelScope.launch {
            repository.deleteEntry(entry)
        }
    }

    // ========================================================
    // Backup / Restore Operations
    // ========================================================

    suspend fun exportBackup(uri: Uri) {
        val jsonContent = backupManager.exportToJson()
        backupManager.writeBackupToUri(uri, jsonContent)
    }

    suspend fun importBackup(uri: Uri) {
        val jsonContent = backupManager.readBackupFromUri(uri)
        backupManager.importFromJson(jsonContent)
    }

    suspend fun exportData(context: android.content.Context, uri: Uri) {
        exportBackup(uri)
    }

    suspend fun importData(context: android.content.Context, uri: Uri) {
        importBackup(uri)
    }
}

/**
 * Factory for creating [KoloViewModel] with constructor parameters.
 */
class KoloViewModelFactory(
    private val repository: KoloRepository,
    private val preferences: KoloPreferences,
    private val backupManager: BackupManager
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(KoloViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return KoloViewModel(repository, preferences, backupManager) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
