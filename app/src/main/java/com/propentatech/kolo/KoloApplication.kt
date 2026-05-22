package com.propentatech.kolo

import android.app.Application
import com.propentatech.kolo.data.backup.BackupManager
import com.propentatech.kolo.data.local.database.KoloDatabase
import com.propentatech.kolo.data.preferences.KoloPreferences
import com.propentatech.kolo.data.repository.KoloRepository

/**
 * Application class for Kolo.
 *
 * Initializes database, repository, and preferences as singletons.
 * This avoids the need for a DI framework (Hilt/Koin) at this stage,
 * while keeping the architecture clean and ready for DI migration later.
 */
class KoloApplication : Application() {

    val database by lazy { KoloDatabase.getDatabase(this) }

    val repository by lazy {
        KoloRepository(
            projectDao = database.projectDao(),
            projectItemDao = database.projectItemDao(),
            savingEntryDao = database.savingEntryDao()
        )
    }

    val preferences by lazy { KoloPreferences(this) }
    
    val backupManager by lazy { BackupManager(this, repository) }
}
