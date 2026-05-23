package com.propentatech.kolo.ui.navigation

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.propentatech.kolo.ui.screens.additem.AddItemScreen
import com.propentatech.kolo.ui.screens.addsaving.AddSavingScreen
import com.propentatech.kolo.ui.screens.createproject.CreateProjectScreen
import com.propentatech.kolo.ui.screens.edititem.EditItemScreen
import com.propentatech.kolo.ui.screens.home.HomeScreen
import com.propentatech.kolo.ui.screens.onboarding.OnboardingScreen
import com.propentatech.kolo.ui.screens.projectdetails.ProjectDetailsScreen
import com.propentatech.kolo.ui.screens.history.SavingsHistoryScreen
import com.propentatech.kolo.ui.screens.settings.SettingsScreen

/**
 * Main navigation graph for the Kolo application.
 *
 * Uses slide animations for forward/back navigation transitions
 * to create a premium, fluid user experience.
 */
@Composable
fun KoloNavGraph(
    navController: NavHostController,
    startDestination: String
) {
    val animDuration = 350

    NavHost(
        navController = navController,
        startDestination = startDestination,
        enterTransition = {
            slideIntoContainer(
                towards = AnimatedContentTransitionScope.SlideDirection.Left,
                animationSpec = tween(animDuration)
            ) + fadeIn(animationSpec = tween(animDuration))
        },
        exitTransition = {
            slideOutOfContainer(
                towards = AnimatedContentTransitionScope.SlideDirection.Left,
                animationSpec = tween(animDuration)
            ) + fadeOut(animationSpec = tween(animDuration))
        },
        popEnterTransition = {
            slideIntoContainer(
                towards = AnimatedContentTransitionScope.SlideDirection.Right,
                animationSpec = tween(animDuration)
            ) + fadeIn(animationSpec = tween(animDuration))
        },
        popExitTransition = {
            slideOutOfContainer(
                towards = AnimatedContentTransitionScope.SlideDirection.Right,
                animationSpec = tween(animDuration)
            ) + fadeOut(animationSpec = tween(animDuration))
        }
    ) {
        // ========================================================
        // Onboarding
        // ========================================================
        composable(Screen.Onboarding.route) {
            OnboardingScreen(
                onFinished = {
                    navController.navigate(Screen.Home.route) {
                        popUpTo(Screen.Onboarding.route) { inclusive = true }
                    }
                }
            )
        }

        // ========================================================
        // Home
        // ========================================================
        composable(Screen.Home.route) {
            HomeScreen(
                onCreateProject = {
                    navController.navigate(Screen.CreateProject.route)
                },
                onProjectClick = { projectId ->
                    navController.navigate(Screen.ProjectDetails.createRoute(projectId))
                },
                onSettingsClick = {
                    navController.navigate(Screen.Settings.route)
                }
            )
        }

        // ========================================================
        // Create Project
        // ========================================================
        composable(Screen.CreateProject.route) {
            CreateProjectScreen(
                onBack = { navController.popBackStack() },
                onProjectCreated = { navController.popBackStack() }
            )
        }

        // ========================================================
        // Project Details
        // ========================================================
        composable(
            route = Screen.ProjectDetails.route,
            arguments = listOf(navArgument("projectId") { type = NavType.LongType })
        ) { backStackEntry ->
            val projectId = backStackEntry.arguments?.getLong("projectId") ?: return@composable
            ProjectDetailsScreen(
                projectId = projectId,
                onBack = { navController.popBackStack() },
                onAddItem = {
                    navController.navigate(Screen.AddItem.createRoute(projectId))
                },
                onAddSaving = {
                    navController.navigate(Screen.AddSaving.createRoute(projectId))
                },
                onViewHistory = {
                    navController.navigate(Screen.SavingsHistory.createRoute(projectId))
                },
                onEditItem = { itemId ->
                    navController.navigate(Screen.EditItem.createRoute(projectId, itemId))
                }
            )
        }

        // ========================================================
        // Add Item
        // ========================================================
        composable(
            route = Screen.AddItem.route,
            arguments = listOf(navArgument("projectId") { type = NavType.LongType })
        ) { backStackEntry ->
            val projectId = backStackEntry.arguments?.getLong("projectId") ?: return@composable
            AddItemScreen(
                projectId = projectId,
                onBack = { navController.popBackStack() },
                onItemAdded = { navController.popBackStack() }
            )
        }

        // ========================================================
        // Edit Item
        // ========================================================
        composable(
            route = Screen.EditItem.route,
            arguments = listOf(
                navArgument("projectId") { type = NavType.LongType },
                navArgument("itemId") { type = NavType.LongType }
            )
        ) { backStackEntry ->
            val projectId = backStackEntry.arguments?.getLong("projectId") ?: return@composable
            val itemId = backStackEntry.arguments?.getLong("itemId") ?: return@composable
            EditItemScreen(
                projectId = projectId,
                itemId = itemId,
                onBack = { navController.popBackStack() },
                onItemUpdated = { navController.popBackStack() }
            )
        }

        // ========================================================
        // Add Saving
        // ========================================================
        composable(
            route = Screen.AddSaving.route,
            arguments = listOf(navArgument("projectId") { type = NavType.LongType })
        ) { backStackEntry ->
            val projectId = backStackEntry.arguments?.getLong("projectId") ?: return@composable
            AddSavingScreen(
                projectId = projectId,
                onBack = { navController.popBackStack() },
                onSavingAdded = { navController.popBackStack() }
            )
        }

        // ========================================================
        // Savings History
        // ========================================================
        composable(
            route = Screen.SavingsHistory.route,
            arguments = listOf(navArgument("projectId") { type = NavType.LongType })
        ) { backStackEntry ->
            val projectId = backStackEntry.arguments?.getLong("projectId") ?: return@composable
            SavingsHistoryScreen(
                projectId = projectId,
                onBack = { navController.popBackStack() }
            )
        }

        // ========================================================
        // Settings
        // ========================================================
        composable(Screen.Settings.route) {
            SettingsScreen(
                onBack = { navController.popBackStack() }
            )
        }
    }
}
