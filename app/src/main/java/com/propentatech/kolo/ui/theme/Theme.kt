package com.propentatech.kolo.ui.theme

import android.app.Activity
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

// ============================================================
// Kolo Dark Color Scheme — Always dark, premium identity
// ============================================================
private val KoloDarkColorScheme = darkColorScheme(
    primary = KoloPrimary,
    onPrimary = KoloOnPrimary,
    primaryContainer = KoloPrimaryDark,
    onPrimaryContainer = KoloOnPrimary,

    secondary = KoloSecondary,
    onSecondary = KoloBackground,
    secondaryContainer = KoloSecondaryVariant,
    onSecondaryContainer = KoloOnBackground,

    tertiary = KoloOnboardingAccent,
    onTertiary = KoloBackground,

    background = KoloBackground,
    onBackground = KoloOnBackground,

    surface = KoloSurface,
    onSurface = KoloOnSurface,
    surfaceVariant = KoloSurfaceVariant,
    onSurfaceVariant = KoloOnSurfaceVariant,

    error = KoloError,
    onError = KoloOnPrimary,

    outline = KoloBorder,
    outlineVariant = KoloBorderLight,
)

// ============================================================
// KoloTheme — Forced dark mode, premium UI
// ============================================================
@Composable
fun KoloTheme(
    content: @Composable () -> Unit
) {
    val colorScheme = KoloDarkColorScheme

    // Set status bar to match our dark theme
    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            window.statusBarColor = colorScheme.background.toArgb()
            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = false
        }
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = KoloTypography,
        content = content
    )
}