package com.propentatech.kolo.ui.localization

import androidx.compose.runtime.compositionLocalOf

/**
 * CompositionLocal for accessing localized strings throughout the Compose tree.
 *
 * Usage:
 *   val strings = LocalStrings.current
 *   Text(strings.homeTitle)
 */
val LocalStrings = compositionLocalOf { FrenchStrings }
