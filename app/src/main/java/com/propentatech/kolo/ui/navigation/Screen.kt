package com.propentatech.kolo.ui.navigation

/**
 * Navigation routes for the Kolo app.
 *
 * Centralized route definitions to avoid hardcoded strings
 * and enable type-safe navigation.
 */
sealed class Screen(val route: String) {

    /** Onboarding — shown only on first launch */
    data object Onboarding : Screen("onboarding")

    /** Main dashboard — list of all projects */
    data object Home : Screen("home")

    /** Create a new project */
    data object CreateProject : Screen("create_project")

    /** Edit an existing project */
    data object EditProject : Screen("edit_project/{projectId}") {
        fun createRoute(projectId: Long) = "edit_project/$projectId"
    }

    /** Project details — items, savings, stats */
    data object ProjectDetails : Screen("project_details/{projectId}") {
        fun createRoute(projectId: Long) = "project_details/$projectId"
    }

    /** Add an item to a project */
    data object AddItem : Screen("add_item/{projectId}") {
        fun createRoute(projectId: Long) = "add_item/$projectId"
    }

    /** Edit an item in a project */
    data object EditItem : Screen("edit_item/{projectId}/{itemId}") {
        fun createRoute(projectId: Long, itemId: Long) = "edit_item/$projectId/$itemId"
    }

    /** Add a savings entry to a project */
    data object AddSaving : Screen("add_saving/{projectId}") {
        fun createRoute(projectId: Long) = "add_saving/$projectId"
    }

    /** Savings history for a project */
    data object SavingsHistory : Screen("savings_history/{projectId}") {
        fun createRoute(projectId: Long) = "savings_history/$projectId"
    }

    /** Settings — language, backup/restore */
    data object Settings : Screen("settings")
}
