package com.propentatech.kolo.data.local.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.propentatech.kolo.data.local.dao.ProjectDao
import com.propentatech.kolo.data.local.dao.ProjectItemDao
import com.propentatech.kolo.data.local.dao.SavingEntryDao
import com.propentatech.kolo.data.local.entities.ProjectEntity
import com.propentatech.kolo.data.local.entities.ProjectItemEntity
import com.propentatech.kolo.data.local.entities.SavingEntryEntity

/**
 * Room database for the Kolo application.
 *
 * Contains three tables:
 *   - projects: User's savings projects
 *   - project_items: Items within each project
 *   - saving_entries: Savings deposits toward projects
 *
 * Thread-safe singleton pattern for database access.
 */
@Database(
    entities = [
        ProjectEntity::class,
        ProjectItemEntity::class,
        SavingEntryEntity::class
    ],
    version = 1,
    exportSchema = false
)
abstract class KoloDatabase : RoomDatabase() {

    abstract fun projectDao(): ProjectDao
    abstract fun projectItemDao(): ProjectItemDao
    abstract fun savingEntryDao(): SavingEntryDao

    companion object {
        @Volatile
        private var INSTANCE: KoloDatabase? = null

        fun getDatabase(context: Context): KoloDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    KoloDatabase::class.java,
                    "kolo_database"
                )
                    .fallbackToDestructiveMigration()
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
}
