package com.propentatech.kolo

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.rememberNavController
import com.propentatech.kolo.ui.localization.LocalStrings
import com.propentatech.kolo.ui.localization.getStringsForLanguage
import com.propentatech.kolo.ui.navigation.KoloNavGraph
import com.propentatech.kolo.ui.navigation.Screen
import com.propentatech.kolo.ui.theme.KoloTheme
import com.propentatech.kolo.viewmodel.KoloViewModel
import com.propentatech.kolo.viewmodel.KoloViewModelFactory

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            // Retrieve application instance
            val app = application as KoloApplication
            
            // Create ViewModel using factory
            val viewModel: KoloViewModel = viewModel(
                factory = KoloViewModelFactory(app.repository, app.preferences, app.backupManager)
            )

            // Collect language state and update composition local
            val languageCode by viewModel.language.collectAsState()
            val strings = getStringsForLanguage(languageCode)

            // Collect onboarding state to determine start destination
            val isOnboardingCompleted by viewModel.isOnboardingCompleted.collectAsState()
            val startDestination = if (isOnboardingCompleted) {
                Screen.Home.route
            } else {
                Screen.Onboarding.route
            }

            // Create NavController
            val navController = rememberNavController()

            CompositionLocalProvider(LocalStrings provides strings) {
                KoloTheme {
                    Surface(
                        modifier = Modifier.fillMaxSize(),
                        color = MaterialTheme.colorScheme.background
                    ) {
                        KoloNavGraph(
                            navController = navController,
                            startDestination = startDestination
                        )
                    }
                }
            }
        }
    }
}