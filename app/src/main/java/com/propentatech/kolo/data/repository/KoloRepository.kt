package com.propentatech.kolo.data.repository

import com.propentatech.kolo.data.local.dao.ProjectDao
import com.propentatech.kolo.data.local.dao.ProjectItemDao
import com.propentatech.kolo.data.local.dao.SavingEntryDao
import com.propentatech.kolo.data.local.entities.ProjectEntity
import com.propentatech.kolo.data.local.entities.ProjectItemEntity
import com.propentatech.kolo.data.local.entities.SavingEntryEntity
import kotlinx.coroutines.flow.Flow

/**
 * Repository for all Kolo data operations.
 *
 * Acts as a single source of truth, abstracting the data layer
 * from the rest of the application. This makes it easy to add
 * cloud sync later without changing ViewModels.
 */
class KoloRepository(
    private val projectDao: ProjectDao,
    private val projectItemDao: ProjectItemDao,
    private val savingEntryDao: SavingEntryDao
) {

    // ========================================================
    // Projects
    // ========================================================

    val allProjects: Flow<List<ProjectEntity>> = projectDao.getAllProjects()
    val projectCount: Flow<Int> = projectDao.getProjectCount()

    fun getProjectById(id: Long): Flow<ProjectEntity?> = projectDao.getProjectById(id)

    suspend fun insertProject(project: ProjectEntity): Long = projectDao.insertProject(project)

    suspend fun updateProject(project: ProjectEntity) = projectDao.updateProject(project)

    suspend fun deleteProject(project: ProjectEntity) = projectDao.deleteProject(project)

    suspend fun deleteProjectById(id: Long) = projectDao.deleteProjectById(id)

    // ========================================================
    // Project Items
    // ========================================================

    fun getItemsByProjectId(projectId: Long): Flow<List<ProjectItemEntity>> =
        projectItemDao.getItemsByProjectId(projectId)

    fun getItemById(itemId: Long): Flow<ProjectItemEntity?> =
        projectItemDao.getItemById(itemId)

    fun getTotalTargetAmount(projectId: Long): Flow<Double> =
        projectItemDao.getTotalTargetAmount(projectId)

    suspend fun insertItem(item: ProjectItemEntity): Long = projectItemDao.insertItem(item)

    suspend fun updateItem(item: ProjectItemEntity) = projectItemDao.updateItem(item)

    suspend fun deleteItem(item: ProjectItemEntity) = projectItemDao.deleteItem(item)

    // ========================================================
    // Saving Entries
    // ========================================================

    fun getEntriesByProjectId(projectId: Long): Flow<List<SavingEntryEntity>> =
        savingEntryDao.getEntriesByProjectId(projectId)

    fun getTotalSavedAmount(projectId: Long): Flow<Double> =
        savingEntryDao.getTotalSavedAmount(projectId)

    fun getEntryCount(projectId: Long): Flow<Int> =
        savingEntryDao.getEntryCount(projectId)

    suspend fun insertEntry(entry: SavingEntryEntity): Long = savingEntryDao.insertEntry(entry)

    suspend fun deleteEntry(entry: SavingEntryEntity) = savingEntryDao.deleteEntry(entry)

    // ========================================================
    // Backup / Restore helpers
    // ========================================================

    suspend fun deleteAllData() {
        savingEntryDao.deleteAllEntries()
        projectItemDao.deleteAllItems()
        projectDao.deleteAllProjects()
    }

    suspend fun restoreData(
        projects: List<ProjectEntity>,
        items: List<ProjectItemEntity>,
        entries: List<SavingEntryEntity>
    ) {
        projectDao.insertAllProjects(projects)
        projectItemDao.insertAllItems(items)
        savingEntryDao.insertAllEntries(entries)
    }
}
